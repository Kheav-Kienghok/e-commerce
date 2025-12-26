package com.diamond.e_commerce.service;

import com.diamond.e_commerce.dto.LoginRequest;
import com.diamond.e_commerce.dto.RegisterRequest;
import com.diamond.e_commerce.dto.UserResponse;
import com.diamond.e_commerce.response.ApiResponse;

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
  ApiResponse<UserResponse> login(LoginRequest request);
}
