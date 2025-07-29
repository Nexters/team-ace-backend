package com.nexters.teamace.fairy.domain;

import static lombok.AccessLevel.PROTECTED;

import com.nexters.teamace.emotion.domain.Emotion;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class Fairy {

    private Long id;
    private String name;
    private String imageUrl;
    private String silhouetteImageUrl;
    private Emotion emotion;

    public Fairy(
            final String name,
            final String imageUrl,
            final String silhouetteImageUrl,
            final Emotion emotion) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.silhouetteImageUrl = silhouetteImageUrl;
        this.emotion = emotion;
    }

    public Fairy(
            final Long id,
            final String name,
            final String imageUrl,
            final String silhouetteImageUrl,
            final Emotion emotion) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.silhouetteImageUrl = silhouetteImageUrl;
        this.emotion = emotion;
    }
}
