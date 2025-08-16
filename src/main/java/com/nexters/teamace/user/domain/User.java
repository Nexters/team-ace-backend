package com.nexters.teamace.user.domain;

import static lombok.AccessLevel.PROTECTED;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MAX_NICKNAME_LENGTH = 20;

    @Getter @EqualsAndHashCode.Include private Long id;
    @Getter private String username;
    @Getter private String nickname;

    public User(final String username, final String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public User(final Long id, final String username, final String nickname) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }
}
