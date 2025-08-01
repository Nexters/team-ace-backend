package com.nexters.teamace.fairy.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquiredFairyJpaRepository extends JpaRepository<AcquiredFairyEntity, Long> {
    List<AcquiredFairyEntity> findAllByUserId(Long userId);
}
