package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.emotion.domain.Emotion;
import com.nexters.teamace.emotion.infrastructure.EmotionEntity;
import com.nexters.teamace.fairy.domain.Fairy;
import org.springframework.stereotype.Component;

@Component
public class FairyMapper {

    public Fairy toDomain(FairyEntity entity) {
        if (entity == null) {
            return null;
        }
        Emotion emotion = null;
        if (entity.getEmotion() != null) {
            emotion =
                    new Emotion(
                            entity.getEmotion().getId(),
                            entity.getEmotion().getName(),
                            entity.getEmotion().getDescription());
        }
        return new Fairy(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getSilhouetteImageUrl(),
                emotion);
    }

    public FairyEntity toEntity(Fairy fairy) {
        if (fairy == null) {
            return null;
        }

        EmotionEntity emotionEntity = null;
        if (fairy.getEmotion() != null) {
            emotionEntity =
                    new EmotionEntity(
                            fairy.getEmotion().getName(), fairy.getEmotion().getDescription());
        }

        return FairyEntity.builder()
                .id(fairy.getId())
                .name(fairy.getName())
                .imageUrl(fairy.getImageUrl())
                .silhouetteImageUrl(fairy.getSilhouetteImageUrl())
                .emotion(emotionEntity)
                .build();
    }
}
