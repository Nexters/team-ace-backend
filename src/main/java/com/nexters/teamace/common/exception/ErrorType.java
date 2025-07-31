package com.nexters.teamace.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ErrorType {
    /** 400 BAD_REQUEST */
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Validation failed"),
    BINDING_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Validation failed"),
    CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Validation failed"),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Invalid argument provided"),

    /** 401 UNAUTHORIZED */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, ErrorCode.E401, "Authentication required"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, ErrorCode.E40100, "Invalid or expired token"),
    INVALID_REFRESH_TOKEN(
            HttpStatus.UNAUTHORIZED, ErrorCode.E40101, "Invalid or expired refresh token"),

    /** 403 FORBIDDEN */
    FORBIDDEN(HttpStatus.FORBIDDEN, ErrorCode.E403, "Access denied"),

    /** 404 NOT_FOUND */
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404, "Resource not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404, "User not found"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404, "Entity not found"),

    /** 409 CONFLICT */
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, ErrorCode.E409, "User already exists"),

    /** 500 INTERNAL_SERVER_ERROR */
    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "An unexpected error occurred"),
    PROMPT_TEMPLATE_NOT_FOUND(
            HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "프롬프트 템플릿이 등록되지 않았습니다"),
    ;

    @Getter private final HttpStatus status;
    @Getter private final ErrorCode code;
    @Getter private final String message;
}
