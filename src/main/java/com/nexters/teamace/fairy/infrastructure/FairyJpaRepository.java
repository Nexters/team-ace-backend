package com.nexters.teamace.fairy.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FairyJpaRepository extends JpaRepository<FairyEntity, Long> {

    List<FairyEntity> findAllByEmotionIdIn(List<Long> emotionIds);
}
