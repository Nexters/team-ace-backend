package com.nexters.teamace.fairy.domain;

import com.nexters.teamace.common.exception.CustomException;
import com.nexters.teamace.emotion.domain.EmotionType;
import com.nexters.teamace.fairy.infrastructure.dto.FairyProjection;
import java.util.List;
import java.util.Optional;

public interface FairyRepository {
    Fairy save(Fairy fairy);

    Optional<Fairy> findById(Long id);

    List<Fairy> findAll();

    List<FairyProjection> findAllByEmotionNames(List<EmotionType> emotionNames);

    default Fairy getById(Long id) {
        return findById(id).orElseThrow(() -> CustomException.FAIRY_NOT_FOUND);
    }
}
