package com.nexters.teamace.conversation.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConversationType {
    CHAT_ASSISTANT("prompts/chat-assistant.txt", "채팅 어시스턴트", MessageConversation.class),
    EMOTION_ANALYSIS("prompts/emotion-analysis.txt", "감정 분석", EmotionSelectConversation.class),

    // Chain of Thought 구현을 위한 3단계 프롬프트
    CHAT_ANALYZER("prompts/chat-analyzer.txt", "채팅 분석", MessageConversation.class),
    CHAT_REASONER("prompts/chat-reasoner.txt", "채팅 추론", MessageConversation.class),
    CHAT_RESPONDER("prompts/chat-responder.txt", "채팅 응답", MessageConversation.class),
    ;

    @Getter private final String resourcePath;
    private final String description;
    @Getter private final Class<?> type;
}
