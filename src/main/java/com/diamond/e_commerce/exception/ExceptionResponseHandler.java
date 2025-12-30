package com.diamond.e_commerce.exception;

import org.springframework.http.HttpStatus;
import com.diamond.e_commerce.response.ApiResponse;

public interface ExceptionResponseHandler {

  default <T> ApiResponse<T> notFound(String message) {
    return ApiResponse.<T>builder()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .message(message != null ? message : "Resource not found")
        .build();
  }

  default <T> ApiResponse<T> unauthorized(String message) {
    return ApiResponse.<T>builder()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .message(message != null ? message : "Unauthorized")
        .build();
  }

  default <T> ApiResponse<T> forbidden(String message) {
    return ApiResponse.<T>builder()
        .statusCode(HttpStatus.FORBIDDEN.value())
        .message(message != null ? message : "Access denied")
        .build();
  }

  default <T> ApiResponse<T> badRequest(String message) {
    return ApiResponse.<T>builder()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .message(message != null ? message : "Bad request")
        .build();
  }

  default <T> ApiResponse<T> internalServerError(String message) {
    return ApiResponse.<T>builder()
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(message != null ? message : "Internal server error")
        .build();
  }
}
