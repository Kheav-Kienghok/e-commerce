package com.diamond.e_commerce.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

/**
 * Generic API response wrapper for all endpoints.
 *
 * @param <T> the type of the response data
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // <-- this hides null fields
@JsonPropertyOrder({ "statusCode", "message", "errors", "data" }) // specify order here
public class ApiResponse<T> {

  private int statusCode;
  private String message;

  private T data; // populated on success
  private Object errors; // populated on error (can be Map, List, String, etc.)

  /*
   * =====================
   * Success factories
   * =====================
   */

  public static <T> ApiResponse<T> success(int statusCode, String message) {
    return success(statusCode, message, null);
  }

  public static <T> ApiResponse<T> success(int statusCode, String message, T data) {
    return ApiResponse.<T>builder()
        .statusCode(statusCode)
        .message(message)
        .data(data)
        .build();
  }

  /*
   * =====================
   * Error factories
   * =====================
   */

  public static <T> ApiResponse<T> error(int statusCode, String message) {
    return error(statusCode, message, null);
  }

  public static <T> ApiResponse<T> error(int statusCode, String message, Object errors) {
    return ApiResponse.<T>builder()
        .statusCode(statusCode)
        .message(message)
        .errors(errors)
        .build();
  }
}