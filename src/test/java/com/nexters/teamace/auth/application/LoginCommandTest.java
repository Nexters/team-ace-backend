package com.nexters.teamace.auth.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("LoginCommand")
class LoginCommandTest {

    @Nested
    @DisplayName("유효한 사용자명으로 생성할 때")
    class Context_when_creating_with_valid_username {

        @Test
        @DisplayName("성공적으로 생성된다")
        void it_creates_successfully() {
            final LoginCommand command = new LoginCommand("validuser");

            then(command.username()).isEqualTo("validuser");
        }
    }

    @Nested
    @DisplayName("null 사용자명으로 생성할 때")
    class Context_when_creating_with_null_username {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new LoginCommand(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("username must not be blank");
        }
    }

    @Nested
    @DisplayName("빈 사용자명으로 생성할 때")
    class Context_when_creating_with_blank_username {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new LoginCommand("  "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("username must not be blank");
        }
    }

    @Nested
    @DisplayName("50자보다 긴 사용자명으로 생성할 때")
    class Context_when_creating_with_username_longer_than_20_characters {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            final String longUsername = "a".repeat(51);

            thenThrownBy(() -> new LoginCommand(longUsername))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Username must be between 1 and 50 characters");
        }
    }
}
