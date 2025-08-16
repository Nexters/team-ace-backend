package com.nexters.teamace.conversation.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConversationType {
    CHAT_ASSISTANT("prompts/chat-assistant.txt", "채팅 어시스턴트", MessageConversation.class),
    EMOTION_ANALYSIS("prompts/emotion-analysis.txt", "감정 분석", EmotionSelectConversation.class),
    ;

    @Getter private final String resourcePath;
    private final String description;
    @Getter private final Class<?> type;
}
