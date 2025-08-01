package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.fairy.domain.AcquiredFairy;
import org.springframework.stereotype.Component;

@Component
public class AcquiredFairyMapper {

    public AcquiredFairy toDomain(AcquiredFairyEntity entity) {
        return new AcquiredFairy(entity.getId(), entity.getFairyId(), entity.getUserId());
    }

    public AcquiredFairyEntity toEntity(AcquiredFairy domain) {
        return new AcquiredFairyEntity(domain.getFairyId(), domain.getUserId());
    }
}
