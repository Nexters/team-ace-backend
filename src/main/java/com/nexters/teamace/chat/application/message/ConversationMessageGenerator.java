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
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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
        final String previousChats = context.previousChats().toString();
        final ChatStage stage = determineChatStage(context.previousChats().size());

        final Map<ConversationContextType, String> variables =
                Map.of(
                        ConversationContextType.PREVIOUS_CONVERSATIONS,
                        previousChats,
                        ConversationContextType.CONVERSATION_STAGE,
                        stage.getDisplayName());

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

    private ChatStage determineChatStage(final int conversationCount) {
        if (conversationCount <= CONTEXT_EXPLORATION_THRESHOLD) {
            return ChatStage.CONTEXT_EXPLORATION;
        }
        return ChatStage.DESIRE_DISCOVERY;
    }
}
