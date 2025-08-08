package com.nexters.teamace.fairy.infrastructure.dto;

import com.nexters.teamace.emotion.domain.EmotionType;

public record FairyProjection(
        Long id, String name, String image, String silhouetteImage, EmotionType emotion) {}
