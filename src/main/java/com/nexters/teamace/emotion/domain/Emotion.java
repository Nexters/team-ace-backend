package com.nexters.teamace.emotion.domain;

import lombok.Getter;

@Getter
public class Emotion {

    @Getter private final Long id;
    @Getter private final String name;
    @Getter private final String description;

    public Emotion(final String name, final String description) {
        this.id = null;
        this.name = name;
        this.description = description;
    }

    public Emotion(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
