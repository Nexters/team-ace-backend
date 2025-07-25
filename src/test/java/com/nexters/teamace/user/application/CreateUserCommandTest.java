package com.nexters.teamace.user.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CreateUserCommand")
class CreateUserCommandTest {

    @Nested
    @DisplayName("when creating with valid username and nickname")
    class Context_when_creating_with_valid_username_and_nickname {

        @Test
        @DisplayName("it creates successfully")
        void it_creates_successfully() {
            final CreateUserCommand command = new CreateUserCommand("validuser", "Valid User");

            then(command.username()).isEqualTo("validuser");
            then(command.nickname()).isEqualTo("Valid User");
        }
    }

    @Nested
    @DisplayName("when creating with null username and valid nickname")
    class Context_when_creating_with_null_username_and_valid_nickname {

        @Test
        @DisplayName("it creates successfully")
        void it_creates_successfully() {
            final CreateUserCommand command = new CreateUserCommand(null, "Valid User");

            then(command.username()).isNull();
            then(command.nickname()).isEqualTo("Valid User");
        }
    }

    @Nested
    @DisplayName("when creating with valid username and null nickname")
    class Context_when_creating_with_valid_username_and_null_nickname {

        @Test
        @DisplayName("it creates successfully")
        void it_creates_successfully() {
            final CreateUserCommand command = new CreateUserCommand("validuser", null);

            then(command.username()).isEqualTo("validuser");
            then(command.nickname()).isNull();
        }
    }

    @Nested
    @DisplayName("when creating with blank username")
    class Context_when_creating_with_blank_username {

        @Test
        @DisplayName("it throws IllegalArgumentException")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new CreateUserCommand("  ", "Valid User"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("username must not be blank");
        }
    }

    @Nested
    @DisplayName("when creating with username longer than 20 characters")
    class Context_when_creating_with_username_longer_than_20_characters {

        @Test
        @DisplayName("it throws IllegalArgumentException")
        void it_throws_IllegalArgumentException() {
            final String longUsername = "a".repeat(21);

            thenThrownBy(() -> new CreateUserCommand(longUsername, "Valid User"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Username must be between 1 and 20 characters");
        }
    }

    @Nested
    @DisplayName("when creating with blank nickname")
    class Context_when_creating_with_blank_nickname {

        @Test
        @DisplayName("it throws IllegalArgumentException")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new CreateUserCommand("validuser", "  "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nickname must not be blank");
        }
    }

    @Nested
    @DisplayName("when creating with nickname longer than 20 characters")
    class Context_when_creating_with_nickname_longer_than_20_characters {

        @Test
        @DisplayName("it throws IllegalArgumentException")
        void it_throws_IllegalArgumentException() {
            final String longNickname = "a".repeat(21);

            thenThrownBy(() -> new CreateUserCommand("validuser", longNickname))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Nickname must be between 1 and 20 characters");
        }
    }

    @Nested
    @DisplayName("when creating with empty username")
    class Context_when_creating_with_empty_username {

        @Test
        @DisplayName("it throws IllegalArgumentException")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new CreateUserCommand("", "Valid User"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("username must not be blank");
        }
    }

    @Nested
    @DisplayName("when creating with empty nickname")
    class Context_when_creating_with_empty_nickname {

        @Test
        @DisplayName("it throws IllegalArgumentException")
        void it_throws_IllegalArgumentException() {
            thenThrownBy(() -> new CreateUserCommand("validuser", ""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nickname must not be blank");
        }
    }
}
