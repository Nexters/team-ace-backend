package com.nexters.teamace.auth.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;

import com.nexters.teamace.common.exception.CustomException;
import com.nexters.teamace.common.utils.UseCaseIntegrationTest;
import com.nexters.teamace.user.domain.User;
import com.nexters.teamace.user.domain.UserRepository;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AuthService")
class AuthServiceTest extends UseCaseIntegrationTest {

    @Autowired private AuthService authService;
    @Autowired private UserRepository userRepository;

    private String generateUserString() {
        return fixtureMonkey
                .giveMeBuilder(String.class)
                .set("$", Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(20))
                .sample();
    }

    @Nested
    @DisplayName("login")
    class Describe_login {

        @Nested
        @DisplayName("when existing user")
        class Context_when_existing_user {

            @Test
            @DisplayName("it returns login result with tokens")
            void it_returns_login_result_with_tokens() {
                // 2025년 1월 1일 00:00:00 UTC
                given(systemHolder.currentTimeMillis()).willReturn(1735689600000L);

                final String username = generateUserString();
                final String nickname = generateUserString();
                userRepository.save(new User(username, nickname));

                final LoginCommand command = new LoginCommand(username);
                final LoginResult result = authService.login(command);

                then(result)
                        .isNotNull()
                        .extracting("username", "accessToken", "refreshToken")
                        .satisfies(
                                values -> {
                                    then(values.get(0)).isEqualTo(username);

                                    // Access Token 검증
                                    then((String) values.get(1))
                                            .isNotNull()
                                            .isNotEmpty()
                                            .matches(
                                                    "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$") // JWT 형식
                                            .contains("eyJhbGciOiJIUzM4NCJ9"); // 헤더 부분은 항상 동일

                                    // Refresh Token 검증
                                    then((String) values.get(2))
                                            .isNotNull()
                                            .isNotEmpty()
                                            .matches(
                                                    "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$") // JWT 형식
                                            .contains("eyJhbGciOiJIUzM4NCJ9"); // 헤더 부분은 항상 동일
                                });
            }
        }

        @Nested
        @DisplayName("when non-existing user")
        class Context_when_non_existing_user {

            @Test
            @DisplayName("it throws CustomException")
            void it_throws_CustomException() {
                final LoginCommand command = new LoginCommand("nonexistent");

                thenThrownBy(() -> authService.login(command))
                        .isInstanceOf(CustomException.class)
                        .hasFieldOrPropertyWithValue(
                                "errorType", CustomException.USER_NOT_FOUND.getErrorType());
            }
        }
    }

    @Nested
    @DisplayName("signup")
    class Describe_signup {

        @Nested
        @DisplayName("when valid username and nickname")
        class Context_when_valid_username_and_nickname {

            @Test
            @DisplayName("it returns signup result with tokens")
            void it_returns_signup_result_with_tokens() {
                // 2025년 1월 1일 00:00:00 UTC
                given(systemHolder.currentTimeMillis()).willReturn(1735689600000L);

                final String username = generateUserString();
                final String nickname = generateUserString();
                final SignupCommand command = new SignupCommand(username, nickname);
                final SignupResult result = authService.signup(command);

                then(result)
                        .isNotNull()
                        .extracting("username", "accessToken", "refreshToken")
                        .satisfies(
                                values -> {
                                    then(values.get(0)).isEqualTo(username);
                                    then((String) values.get(1)).isNotNull().isNotEmpty();
                                    then((String) values.get(2)).isNotNull().isNotEmpty();
                                });

                final User savedUser = userRepository.getByUsername(username);
                then(savedUser.getNickname()).isEqualTo(nickname);
            }
        }

        @Nested
        @DisplayName("when username already exists")
        class Context_when_username_already_exists {

            @Test
            @DisplayName("it throws CustomException")
            void it_throws_CustomException() {
                final String existingUsername = generateUserString();
                final String existingNickname = generateUserString();
                userRepository.save(new User(existingUsername, existingNickname));

                final String anotherNickname = generateUserString();
                final SignupCommand command = new SignupCommand(existingUsername, anotherNickname);

                thenThrownBy(() -> authService.signup(command))
                        .isInstanceOf(CustomException.class)
                        .hasFieldOrPropertyWithValue(
                                "errorType", CustomException.USER_ALREADY_EXISTS.getErrorType());
            }
        }
    }

    @Nested
    @DisplayName("refreshToken")
    class Describe_refreshToken {

        @Nested
        @DisplayName("when valid refresh token")
        class Context_when_valid_refresh_token {

            @Test
            @DisplayName("it returns new access token")
            void it_returns_new_access_token() {
                // 현재 시간을 사용하여 유효한 토큰 생성
                final long currentTime = System.currentTimeMillis();

                // 모든 호출에 대해 현재 시간 반환
                given(systemHolder.currentTimeMillis()).willReturn(currentTime);

                final String username = generateUserString();
                final String nickname = generateUserString();
                userRepository.save(new User(username, nickname));

                final LoginCommand loginCommand = new LoginCommand(username);
                final LoginResult loginResult = authService.login(loginCommand);

                final RefreshTokenCommand command =
                        new RefreshTokenCommand(loginResult.refreshToken());
                final RefreshTokenResult result = authService.refreshToken(command);

                then(result.accessToken()).isNotNull().isNotEmpty();
            }
        }

        @Nested
        @DisplayName("when invalid refresh token")
        class Context_when_invalid_refresh_token {

            @Test
            @DisplayName("it throws CustomException")
            void it_throws_CustomException() {
                final RefreshTokenCommand command = new RefreshTokenCommand("invalid-token");

                thenThrownBy(() -> authService.refreshToken(command))
                        .isInstanceOf(CustomException.class)
                        .hasFieldOrPropertyWithValue(
                                "errorType", CustomException.INVALID_REFRESH_TOKEN.getErrorType());
            }
        }
    }
}
