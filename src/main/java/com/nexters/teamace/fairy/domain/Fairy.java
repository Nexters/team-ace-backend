package com.nexters.teamace.fairy.domain;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class Fairy {

    private Long id;
    private String name;
    private String imageUrl;
    private String silhouetteImageUrl;
    private Long emotionId;

    public Fairy(
            final String name,
            final String imageUrl,
            final String silhouetteImageUrl,
            final Long emotionId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.silhouetteImageUrl = silhouetteImageUrl;
        this.emotionId = emotionId;
    }

    public Fairy(
            final Long id,
            final String name,
            final String imageUrl,
            final String silhouetteImageUrl,
            final Long emotionId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.silhouetteImageUrl = silhouetteImageUrl;
        this.emotionId = emotionId;
    }
}
