package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.emotion.infrastructure.EmotionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fairies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FairyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fairy_id")
    private Long id;

    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "silhouette_image_url")
    private String silhouetteImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id")
    private EmotionEntity emotion;

    @Builder
    public FairyEntity(
            Long id,
            String name,
            String imageUrl,
            String silhouetteImageUrl,
            EmotionEntity emotion) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.silhouetteImageUrl = silhouetteImageUrl;
        this.emotion = emotion;
    }
}
