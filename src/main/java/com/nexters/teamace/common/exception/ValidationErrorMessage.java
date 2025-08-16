package com.nexters.teamace.common.exception;

import com.nexters.teamace.user.domain.User;

public class ValidationErrorMessage {

    /** Auth */
    public static final String REFRESH_TOKEN_NOT_BLANK = "refresh token must not be blank";

    /** User */
    public static final String USER_ID_NOT_NULL = "User ID cannot be null";

    public static final String USER_ID_POSITIVE = "User ID must be greater than or equal to 1";
    public static final String USERNAME_SIZE =
            "Username must be between 1 and " + User.MAX_USERNAME_LENGTH + " characters";

    public static final String NICKNAME_SIZE =
            "Nickname must be between 1 and " + User.MAX_NICKNAME_LENGTH + " characters";
    public static final String USERNAME_NOT_BLANK = "username must not be blank";
    public static final String NICKNAME_NOT_BLANK = "nickname must not be blank";

    /** Chat */
    public static final String MESSAGE_NOT_BLANK = "message must not be blank";

    public static final String CHAT_ROOM_ID_NOT_NULL = "ChatRoom ID cannot be null";

    public static final String CHAT_ROOM_ID_POSITIVE =
            "ChatRoom ID must be greater than or equal to 1";
    public static final String PREVIOUS_CHATS_NOT_NULL = "Previous chats cannot be null";
    public static final String CHAT_CONTEXT_NOT_NULL = "ChatContext cannot be null";
    public static final String CHAT_ROOM_ACCESS_DENIED = "채팅방에 접근할 권한이 없습니다.";

    /** Letter */
    public static final String LETTER_CONTENTS_NOT_BLANK = "Letter contents must not be blank";

    /** Fairy */
    public static final String FAIRY_ID_NOT_NULL = "Fairy ID cannot be null";

    public static final String FAIRY_ID_ID_POSITIVE = "Fairy ID must be greater than or equal to 1";
}
