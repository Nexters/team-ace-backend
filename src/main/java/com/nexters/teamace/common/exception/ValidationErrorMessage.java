package com.nexters.teamace.common.exception;

public class ValidationErrorMessage {

    /** Auth */
    public static final String REFRESH_TOKEN_NOT_BLANK = "refresh token must not be blank";

    /** User */
    public static final String USERNAME_SIZE = "Username must be between 1 and 20 characters";

    public static final String NICKNAME_SIZE = "Nickname must be between 1 and 20 characters";
    public static final String USERNAME_NOT_BLANK = "username must not be blank";
    public static final String NICKNAME_NOT_BLANK = "nickname must not be blank";

    /** Chat */
    public static final String MESSAGE_NOT_BLANK = "message must not be blank";

    public static final String CHAT_ROOM_ID_NOT_NULL = "ChatRoom ID cannot be null";

    public static final String CHAT_ROOM_ID_POSITIVE =
            "ChatRoom ID must be greater than or equal to 1";
    public static final String PREVIOUS_CHATS_NOT_NULL = "Previous chats cannot be null";
    public static final String CHAT_CONTEXT_NOT_NULL = "ChatContext cannot be null";
}
