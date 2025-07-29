package com.nexters.teamace.emotion.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionJpaRepository extends JpaRepository<EmotionEntity, Long> {
    List<EmotionEntity> findAllByNameIn(List<String> names);
}
