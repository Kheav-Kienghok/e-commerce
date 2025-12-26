package com.diamond.e_commerce.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.diamond.e_commerce.dto.RegisterRequest;
import com.diamond.e_commerce.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
    ApiResponse<Void> response = ApiResponse.<Void>builder()
        .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
        .message("Request method not supported")
        .build();
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new LinkedHashMap<>();

    // Get field order from DTO class
    String[] fieldOrder = java.util.Arrays.stream(RegisterRequest.class.getDeclaredFields())
        .map(f -> f.getName())
        .toArray(String[]::new);

    ex.getBindingResult().getFieldErrors().stream()
        .sorted((e1, e2) -> {
          int index1 = java.util.Arrays.asList(fieldOrder).indexOf(e1.getField());
          int index2 = java.util.Arrays.asList(fieldOrder).indexOf(e2.getField());
          return Integer.compare(index1, index2);
        })
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .message("Validation failed")
        .data(errors)
        .build();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<Void>> handleEmptyRequestBody(HttpMessageNotReadableException ex) {
    ApiResponse<Void> response = ApiResponse.<Void>builder()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .message("Request body is missing or invalid")
        .build();
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error(500, "Internal server error"));
  }
}