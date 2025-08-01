package com.nexters.teamace.chat.application;

import com.nexters.teamace.common.exception.ValidationErrorMessage;

public record AllChatQuery(Long chatRoomId) {
    public AllChatQuery {
        if (chatRoomId == null) {
            throw new IllegalArgumentException(ValidationErrorMessage.CHAT_ROOM_ID_NOT_NULL);
        }
        if (chatRoomId < 1) {
            throw new IllegalArgumentException(ValidationErrorMessage.CHAT_ROOM_ID_POSITIVE);
        }
    }
}
