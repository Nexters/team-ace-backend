package com.nexters.teamace.common.presentation;

public record ApiResponse<T>(
        boolean success,
        T data,
        ErrorMessage error
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(false, null, new ErrorMessage(code, message));
    }
}
