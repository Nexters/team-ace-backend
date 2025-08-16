package com.nexters.teamace.auth.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("SignupCommand")
class SignupCommandTest {

    @Nested
    @DisplayName("유효한 사용자명과 닉네임으로 생성할 때")
    class Context_when_creating_with_valid_username_and_nickname {

        @Test
        @DisplayName("성공적으로 생성된다")
        void it_creates_successfully() {
            final SignupCommand command = new SignupCommand("validuser", "Valid User");

            then(command.username()).isEqualTo("validuser");
            then(command.nickname()).isEqualTo("Valid User");
        }
    }

    @Nested
    @DisplayName("null 사용자명으로 생성할 때")
    class Context_when_creating_with_null_username {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new SignupCommand(null, "Valid User"))
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
            thenThrownBy(() -> new SignupCommand("  ", "Valid User"))
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

            thenThrownBy(() -> new SignupCommand(longUsername, "Valid User"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Username must be between 1 and 50 characters");
        }
    }

    @Nested
    @DisplayName("null 닉네임으로 생성할 때")
    class Context_when_creating_with_null_nickname {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new SignupCommand("validuser", null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nickname must not be blank");
        }
    }

    @Nested
    @DisplayName("빈 닉네임으로 생성할 때")
    class Context_when_creating_with_blank_nickname {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new SignupCommand("validuser", "  "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nickname must not be blank");
        }
    }

    @Nested
    @DisplayName("20자보다 긴 닉네임으로 생성할 때")
    class Context_when_creating_with_nickname_longer_than_20_characters {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            final String longNickname = "a".repeat(21);

            thenThrownBy(() -> new SignupCommand("validuser", longNickname))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Nickname must be between 1 and 20 characters");
        }
    }
}
