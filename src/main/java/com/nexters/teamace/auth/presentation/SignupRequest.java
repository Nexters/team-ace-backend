package com.nexters.teamace.auth.presentation;

import com.nexters.teamace.common.exception.ValidationErrorMessage;
import com.nexters.teamace.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = ValidationErrorMessage.USERNAME_NOT_BLANK)
                @Size(
                        min = 1,
                        max = User.MAX_USERNAME_LENGTH,
                        message = ValidationErrorMessage.USERNAME_SIZE)
                String username,
        @NotBlank(message = ValidationErrorMessage.NICKNAME_NOT_BLANK)
                @Size(
                        min = 1,
                        max = User.MAX_NICKNAME_LENGTH,
                        message = ValidationErrorMessage.NICKNAME_SIZE)
                String nickname) {}
