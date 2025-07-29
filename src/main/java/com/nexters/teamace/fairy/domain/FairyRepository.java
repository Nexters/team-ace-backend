package com.nexters.teamace.fairy.domain;

import java.util.List;
import java.util.Optional;

public interface FairyRepository {
    Fairy save(Fairy fairy);

    Optional<Fairy> findById(Long id);

    List<Fairy> findAllByEmotionIdIn(List<Long> emotionIds);
}
