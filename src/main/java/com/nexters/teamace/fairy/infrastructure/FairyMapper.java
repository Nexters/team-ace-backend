package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.emotion.infrastructure.EmotionEntity;
import com.nexters.teamace.fairy.domain.Fairy;
import org.springframework.stereotype.Component;

@Component
public class FairyMapper {

    public Fairy toDomain(FairyEntity entity) {
        if (entity == null) {
            return null;
        }

        Long emotionId = (entity.getEmotion() != null) ? entity.getEmotion().getId() : null;

        return new Fairy(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getSilhouetteImageUrl(),
                emotionId);
    }

    public FairyEntity toEntity(Fairy fairy, EmotionEntity emotionEntity) {
        if (fairy == null) {
            return null;
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
