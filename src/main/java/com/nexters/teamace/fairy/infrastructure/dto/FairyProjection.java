package com.nexters.teamace.fairy.infrastructure.dto;

import com.nexters.teamace.emotion.domain.EmotionName;

public record FairyProjection(
        Long id, String name, String image, String silhouetteImage, EmotionName emotionName) {}
