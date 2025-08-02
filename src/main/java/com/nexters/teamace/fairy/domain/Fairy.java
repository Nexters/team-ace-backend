package com.nexters.teamace.fairy.domain;

import lombok.Getter;

@Getter
public class Fairy {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final String silhouetteImageUrl;
    private final Long emotionId;

    public Fairy(
            final String name,
            final String imageUrl,
            final String silhouetteImageUrl,
            final Long emotionId) {
        this.id = null;
        this.name = name;
        this.imageUrl = imageUrl;
        this.silhouetteImageUrl = silhouetteImageUrl;
        this.emotionId = emotionId;
    }

    public Fairy(Long id, String name, String imageUrl, String silhouetteImageUrl, Long emotionId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.silhouetteImageUrl = silhouetteImageUrl;
        this.emotionId = emotionId;
    }
}
