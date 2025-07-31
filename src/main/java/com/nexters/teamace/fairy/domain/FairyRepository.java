package com.nexters.teamace.fairy.domain;

import com.nexters.teamace.fairy.infrastructure.dto.FairyProjection;
import java.util.List;
import java.util.Optional;

public interface FairyRepository {
    Fairy save(Fairy fairy);

    Optional<Fairy> findById(Long id);

    List<FairyProjection> findAllByEmotionNames(List<String> emotionNames);
}
