package com.nexters.teamace.chat.application.message;

import com.nexters.teamace.chat.domain.ChatContext;
import com.nexters.teamace.chat.domain.ChatMessageGenerator;
import com.nexters.teamace.chat.domain.ChatStage;
import com.nexters.teamace.conversation.application.ConversationContext;
import com.nexters.teamace.conversation.application.ConversationService;
import com.nexters.teamace.conversation.domain.ConversationContextType;
import com.nexters.teamace.conversation.domain.ConversationType;
import com.nexters.teamace.conversation.domain.MessageConversation;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Primary
@Component
@RequiredArgsConstructor
class ConversationMessageGenerator implements ChatMessageGenerator {

    private static final String FIRST_USER_MESSAGE = "질문을 진행해주세요.";
    private static final int CONTEXT_EXPLORATION_THRESHOLD = 2;

    private final ConversationService conversationService;

    @Override
    public String generateFirstMessage() {
        final Map<ConversationContextType, String> variables =
                Map.of(
                        ConversationContextType.PREVIOUS_CONVERSATIONS,
                        "",
                        ConversationContextType.CONVERSATION_STAGE,
                        ChatStage.EMOTION_RECOGNITION.getDisplayName());

        final ConversationContext context = new ConversationContext("first-message", variables);

        final MessageConversation result =
                conversationService.chat(
                        MessageConversation.class,
                        ConversationType.CHAT_ASSISTANT,
                        context,
                        FIRST_USER_MESSAGE);

        return result.message();
    }

    @Override
    public String generateResponseMessage(final String userMessage, final ChatContext context) {
        return generateResponseWithChainOfThought(userMessage, context);
    }

    /** Chain of Thought 구현: 분석 → 추론 → 응답 생성의 3단계 과정 */
    private String generateResponseWithChainOfThought(
            final String userMessage, final ChatContext context) {
        try {
            final String previousChats = context.previousChats().toString();

            // 1단계: 사용자 발화 분석
            final String analysisResult = analyzeUserMessage(userMessage, previousChats);
            log.debug("Analysis result: {}", analysisResult);

            // 2단계: 분석 결과를 바탕으로 응답 전략 수립
            final String strategyResult = reasonResponseStrategy(analysisResult, previousChats);
            log.debug("Strategy result: {}", strategyResult);

            // 3단계: 전략을 바탕으로 최종 응답 생성
            final String finalResponse =
                    generateFinalResponse(strategyResult, analysisResult, previousChats);
            log.debug("Final response generated");

            return finalResponse;

        } catch (Exception e) {
            log.error("Chain of Thought 처리 중 오류 발생, 기본 방식으로 fallback", e);
            return generateResponseWithFallback(userMessage, context);
        }
    }

    /** 1단계: 사용자 발화 분석 */
    private String analyzeUserMessage(final String userMessage, final String previousChats) {
        final Map<ConversationContextType, String> variables =
                Map.of(
                        ConversationContextType.PREVIOUS_CONVERSATIONS, previousChats,
                        ConversationContextType.USER_MESSAGE, userMessage);

        final ConversationContext context = new ConversationContext("analyze", variables);

        final MessageConversation result =
                conversationService.chat(
                        MessageConversation.class,
                        ConversationType.CHAT_ANALYZER,
                        context,
                        userMessage);

        return result.message();
    }

    /** 2단계: 응답 전략 수립 */
    private String reasonResponseStrategy(final String analysisResult, final String previousChats) {
        final Map<ConversationContextType, String> variables =
                Map.of(
                        ConversationContextType.PREVIOUS_CONVERSATIONS, previousChats,
                        ConversationContextType.ANALYSIS_RESULT, analysisResult);

        final ConversationContext context = new ConversationContext("reason", variables);

        final MessageConversation result =
                conversationService.chat(
                        MessageConversation.class, ConversationType.CHAT_REASONER, context, ".");

        return result.message();
    }

    /** 3단계: 최종 응답 생성 */
    private String generateFinalResponse(
            final String strategyResult, final String analysisResult, final String previousChats) {
        final Map<ConversationContextType, String> variables =
                Map.of(
                        ConversationContextType.PREVIOUS_CONVERSATIONS, previousChats,
                        ConversationContextType.ANALYSIS_RESULT, analysisResult,
                        ConversationContextType.STRATEGY_PLAN, strategyResult);

        final ConversationContext context = new ConversationContext("respond", variables);

        final MessageConversation result =
                conversationService.chat(
                        MessageConversation.class, ConversationType.CHAT_RESPONDER, context, ".");

        return result.message();
    }

    /** Chain of Thought 실패 시 기본 방식으로 fallback */
    private String generateResponseWithFallback(
            final String userMessage, final ChatContext context) {
        final String previousChats = context.previousChats().toString();

        final Map<ConversationContextType, String> variables =
                Map.of(ConversationContextType.PREVIOUS_CONVERSATIONS, previousChats);

        final ConversationContext conversationContext =
                new ConversationContext(Long.toString(context.chatRoomId()), variables);

        final MessageConversation result =
                conversationService.chat(
                        MessageConversation.class,
                        ConversationType.CHAT_ASSISTANT,
                        conversationContext,
                        userMessage);

        return result.message();
    }
}
