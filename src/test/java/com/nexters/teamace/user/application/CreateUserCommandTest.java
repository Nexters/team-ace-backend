package com.nexters.teamace.user.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CreateUserCommand")
class CreateUserCommandTest {

    @Nested
    @DisplayName("유효한 사용자명과 닉네임으로 생성할 때")
    class Context_when_creating_with_valid_username_and_nickname {

        @Test
        @DisplayName("성공적으로 생성된다")
        void it_creates_successfully() {
            final CreateUserCommand command = new CreateUserCommand("validuser", "Valid User");

            then(command.username()).isEqualTo("validuser");
            then(command.nickname()).isEqualTo("Valid User");
        }
    }

    @Nested
    @DisplayName("null 사용자명과 유효한 닉네임으로 생성할 때")
    class Context_when_creating_with_null_username_and_valid_nickname {

        @Test
        @DisplayName("성공적으로 생성된다")
        void it_creates_successfully() {
            final CreateUserCommand command = new CreateUserCommand(null, "Valid User");

            then(command.username()).isNull();
            then(command.nickname()).isEqualTo("Valid User");
        }
    }

    @Nested
    @DisplayName("유효한 사용자명과 null 닉네임으로 생성할 때")
    class Context_when_creating_with_valid_username_and_null_nickname {

        @Test
        @DisplayName("성공적으로 생성된다")
        void it_creates_successfully() {
            final CreateUserCommand command = new CreateUserCommand("validuser", null);

            then(command.username()).isEqualTo("validuser");
            then(command.nickname()).isNull();
        }
    }

    @Nested
    @DisplayName("빈 사용자명으로 생성할 때")
    class Context_when_creating_with_blank_username {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new CreateUserCommand("  ", "Valid User"))
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

            thenThrownBy(() -> new CreateUserCommand(longUsername, "Valid User"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Username must be between 1 and 50 characters");
        }
    }

    @Nested
    @DisplayName("빈 닉네임으로 생성할 때")
    class Context_when_creating_with_blank_nickname {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new CreateUserCommand("validuser", "  "))
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

            thenThrownBy(() -> new CreateUserCommand("validuser", longNickname))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Nickname must be between 1 and 20 characters");
        }
    }

    @Nested
    @DisplayName("빈 문자열 사용자명으로 생성할 때")
    class Context_when_creating_with_empty_username {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new CreateUserCommand("", "Valid User"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("username must not be blank");
        }
    }

    @Nested
    @DisplayName("빈 문자열 닉네임으로 생성할 때")
    class Context_when_creating_with_empty_nickname {

        @Test
        @DisplayName("IllegalArgumentException을 던진다")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new CreateUserCommand("validuser", ""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nickname must not be blank");
        }
    }
}
