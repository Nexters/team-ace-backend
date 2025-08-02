package com.nexters.teamace.fairy.domain;

import lombok.Getter;

@Getter
public class AcquiredFairy {
    private final Long id;
    private final Long fairyId;
    private final Long userId;

    public AcquiredFairy(Long id, Long fairyId, Long userId) {
        this.id = id;
        this.fairyId = fairyId;
        this.userId = userId;
    }

    public AcquiredFairy(Long fairyId, Long userId) {
        this.id = null;
        this.fairyId = fairyId;
        this.userId = userId;
    }
}
