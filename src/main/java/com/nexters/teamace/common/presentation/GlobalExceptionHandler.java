package com.nexters.teamace.common.presentation;

import com.nexters.teamace.common.exception.CustomException;
import com.nexters.teamace.common.exception.ErrorType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e) {
        final String message = e.getMessage();
        log.debug("Validation error occurred: {}", message, e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<Map<String, String>> validationErrors =
                fieldErrors.stream()
                        .map(
                                error ->
                                        Map.of(
                                                "field", error.getField(),
                                                "message", error.getDefaultMessage()))
                        .toList();
        return new ResponseEntity<>(
                ApiResponse.error(ErrorType.METHOD_ARGUMENT_NOT_VALID, message, validationErrors),
                ErrorType.METHOD_ARGUMENT_NOT_VALID.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(
            MethodArgumentTypeMismatchException e) {
        final String message = e.getMessage();
        log.debug("Method argument type mismatch error occurred: {}", message, e);

        final Map<String, String> errorDetails =
                Map.ofEntries(
                        Map.entry("parameter", e.getName()),
                        Map.entry(
                                "message",
                                "invalid type: excpected " + e.getRequiredType().getSimpleName()));
        return new ResponseEntity<>(
                ApiResponse.error(
                        ErrorType.METHOD_ARGUMENT_NOT_VALID, message, List.of(errorDetails)),
                ErrorType.METHOD_ARGUMENT_NOT_VALID.getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException e) {
        final String message = e.getMessage();
        log.debug("Binding error occurred: {}", message, e);

        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        final List<Map<String, String>> validationErrors =
                fieldErrors.stream()
                        .map(
                                error ->
                                        Map.of(
                                                "field", error.getField(),
                                                "message", error.getDefaultMessage()))
                        .toList();

        return new ResponseEntity<>(
                ApiResponse.error(ErrorType.BINDING_ERROR, message, validationErrors),
                ErrorType.BINDING_ERROR.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
            ConstraintViolationException e) {
        final String message = e.getMessage();
        log.debug("Constraint violation occurred: {}", message);

        return new ResponseEntity<>(
                ApiResponse.error(ErrorType.CONSTRAINT_VIOLATION, message),
                ErrorType.CONSTRAINT_VIOLATION.getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException e) {
        final String message = e.getMessage();
        log.debug("Invalid argument provided: {}", message, e);

        return new ResponseEntity<>(
                ApiResponse.error(ErrorType.INVALID_ARGUMENT, message),
                ErrorType.INVALID_ARGUMENT.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(
            final AuthenticationException e) {
        log.debug("Authentication Exception occurred: {} ", e.getMessage(), e);
        return new ResponseEntity<>(
                ApiResponse.error(ErrorType.UNAUTHORIZED, e.getMessage()),
                ErrorType.UNAUTHORIZED.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(
            final AccessDeniedException e) {
        log.warn("AccessDenied Exception occurred: {} ", e.getMessage(), e);
        return new ResponseEntity<>(
                ApiResponse.error(ErrorType.FORBIDDEN, e.getMessage()),
                ErrorType.FORBIDDEN.getStatus());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(
            NoResourceFoundException e) {
        log.warn("Resource not found error occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>(
                ApiResponse.error(ErrorType.RESOURCE_NOT_FOUND),
                ErrorType.RESOURCE_NOT_FOUND.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundException(
            NoResourceFoundException e) {
        log.warn("Entity not found error occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>(
                ApiResponse.error(ErrorType.ENTITY_NOT_FOUND),
                ErrorType.ENTITY_NOT_FOUND.getStatus());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoSuchElement(CustomException e) {
        final String message = e.getMessage();
        log.warn("CustomException: {}", message);
        return new ResponseEntity<>(
                ApiResponse.error(e.getErrorType(), message, e.getData()),
                e.getErrorType().getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unexpected error occurred: ", e);
        return new ResponseEntity<>(
                ApiResponse.error(ErrorType.INTERNAL_SERVER_ERROR),
                ErrorType.INTERNAL_SERVER_ERROR.getStatus());
    }
}
