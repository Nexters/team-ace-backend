package com.nexters.teamace.fairy.application;

import static com.nexters.teamace.common.exception.ValidationErrorMessage.FAIRY_ID_NOT_NULL;
import static com.nexters.teamace.common.exception.ValidationErrorMessage.USER_ID_NOT_NULL;

public record AcquiredFairyCommand(Long fairyId, Long userId) {
    public AcquiredFairyCommand {
        if (fairyId == null) {
            throw new IllegalArgumentException(FAIRY_ID_NOT_NULL);
        }
        if (userId == null) {
            throw new IllegalArgumentException(USER_ID_NOT_NULL);
        }
    }
}
