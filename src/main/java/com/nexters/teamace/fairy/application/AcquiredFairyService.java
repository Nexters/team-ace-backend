package com.nexters.teamace.fairy.application;

import com.nexters.teamace.fairy.domain.AcquiredFairy;
import com.nexters.teamace.fairy.domain.AcquiredFairyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcquiredFairyService {

    private final AcquiredFairyRepository acquiredFairyRepository;

    public AcquiredFairy save(AcquiredFairyCommand acquiredFairyCommand) {
        return acquiredFairyRepository.save(
                new AcquiredFairy(acquiredFairyCommand.fairyId(), acquiredFairyCommand.userId()));
    }
}
