package com.nexters.teamace.auth.application;

import static com.nexters.teamace.common.exception.ValidationErrorMessage.*;

import com.nexters.teamace.user.domain.User;
import org.springframework.util.StringUtils;

public record LoginCommand(String username) {
    public LoginCommand {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException(USERNAME_NOT_BLANK);
        }
        if (username.length() > User.MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException(USERNAME_SIZE);
        }
    }
}
