package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.common.infrastructure.entity.BaseEntity;
import com.nexters.teamace.emotion.infrastructure.EmotionEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fairies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FairyEntity extends BaseEntity {

    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "silhouette_image_url")
    private String silhouetteImageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id")
    private EmotionEntity emotion;

    @Builder
    public FairyEntity(
            Long id,
            String name,
            String imageUrl,
            String silhouetteImageUrl,
            EmotionEntity emotion) {
        super(id);
        this.name = name;
        this.imageUrl = imageUrl;
        this.silhouetteImageUrl = silhouetteImageUrl;
        this.emotion = emotion;
    }
}
