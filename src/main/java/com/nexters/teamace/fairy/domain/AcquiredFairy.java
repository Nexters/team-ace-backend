package com.nexters.teamace.fairy.domain;

import lombok.Getter;

@Getter
public class AcquiredFairy {
    private Long id;
    private Long fairyId;
    private Long userId;

    public AcquiredFairy(Long id, Long fairyId, Long userId) {
        this.id = id;
        this.fairyId = fairyId;
        this.userId = userId;
    }

    public AcquiredFairy(Long fairyId, Long userId) {
        this(null, fairyId, userId);
    }
}
