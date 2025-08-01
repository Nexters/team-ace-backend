package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.fairy.domain.AcquiredFairy;
import com.nexters.teamace.fairy.domain.AcquiredFairyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AcquiredFairyRepositoryImpl implements AcquiredFairyRepository {

    private final AcquiredFairyJpaRepository acquiredFairyJpaRepository;
    private final AcquiredFairyMapper acquiredFairyMapper;

    @Override
    public AcquiredFairy save(AcquiredFairy acquiredFairy) {
        AcquiredFairyEntity entity = acquiredFairyMapper.toEntity(acquiredFairy);
        AcquiredFairyEntity savedEntity = acquiredFairyJpaRepository.save(entity);
        return acquiredFairyMapper.toDomain(savedEntity);
    }
}
