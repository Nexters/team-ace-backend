package com.nexters.teamace.common.application;

import com.nexters.teamace.common.exception.ErrorType;

public interface AlertService {
    void error(ErrorType errorType, String message);
}
