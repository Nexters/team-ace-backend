package com.nexters.teamace.user.application;

import static com.nexters.teamace.common.exception.ValidationErrorMessage.*;

import com.nexters.teamace.user.domain.User;
import org.springframework.util.StringUtils;

public record CreateUserCommand(String username, String nickname) {
    public CreateUserCommand {
        if (username != null) {
            if (!StringUtils.hasText(username)) {
                throw new IllegalArgumentException(USERNAME_NOT_BLANK);
            }
            if (username.length() > User.MAX_USERNAME_LENGTH) {
                throw new IllegalArgumentException(USERNAME_SIZE);
            }
        }
        if (nickname != null) {
            if (!StringUtils.hasText(nickname)) {
                throw new IllegalArgumentException(NICKNAME_NOT_BLANK);
            }
            if (nickname.length() > User.MAX_NICKNAME_LENGTH) {
                throw new IllegalArgumentException(NICKNAME_SIZE);
            }
        }
    }
}
