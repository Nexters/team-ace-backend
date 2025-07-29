package com.nexters.teamace.emotion.domain;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
public class Emotion {

    @Getter private Long id;
    @Getter private String name;
    @Getter private String description;

    public Emotion(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public Emotion(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
