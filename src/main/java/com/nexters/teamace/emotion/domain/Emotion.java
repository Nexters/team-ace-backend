package com.nexters.teamace.emotion.domain;

import lombok.Getter;

@Getter
public class Emotion {

    @Getter private final Long id;

    @Getter private final EmotionType name;

    @Getter private final String description;

    public Emotion(final EmotionType name, final String description) {
        this.id = null;
        this.name = name;
        this.description = description;
    }

    public Emotion(final Long id, final EmotionType name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
