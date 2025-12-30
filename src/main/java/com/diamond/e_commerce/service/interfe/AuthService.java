package com.diamond.e_commerce.service.interfe;

import java.util.Map;

import com.diamond.e_commerce.dto.LoginRequest;
import com.diamond.e_commerce.dto.RegisterRequest;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.response.UserResponse;

public interface AuthService {

  /**
   * Register a new user.
   *
   * @param request the registration request DTO
   * @return ApiResponse containing UserResponse
   */
  ApiResponse<UserResponse> register(RegisterRequest request);

  /**
   * Login a user.
   *
   * @param request the login request DTO
   * @return ApiResponse containing JWT token or user info
   */
  ApiResponse<Map<String, String>> login(LoginRequest request);
}
