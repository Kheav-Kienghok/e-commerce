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
public class GlobalExceptionHandler implements ExceptionResponseHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(notFound(ex.getMessage()));
  }

  @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
  public ResponseEntity<ApiResponse<Void>> handleAccessDenied(
      org.springframework.security.access.AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(forbidden(ex.getMessage()));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(badRequest("Request method not supported"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new LinkedHashMap<>();

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

    return ResponseEntity.badRequest().body(
        ApiResponse.<Map<String, String>>builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .message("Validation failed")
            .data(errors)
            .build());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<Void>> handleEmptyRequestBody(HttpMessageNotReadableException ex) {
    return ResponseEntity.badRequest()
        .body(badRequest("Request body is missing or invalid"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(internalServerError(ex.getMessage()));
  }
}
