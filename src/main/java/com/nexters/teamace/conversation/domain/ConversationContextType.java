package com.nexters.teamace.conversation.domain;

public enum ConversationContextType {
    PREVIOUS_CONVERSATIONS,
    CONVERSATION_STAGE,
    EMOTION_CANDIDATES,

    // Chain of Thought 구현을 위한 추가 컨텍스트 타입
    USER_MESSAGE,
    ANALYSIS_RESULT,
    STRATEGY_PLAN,
}
