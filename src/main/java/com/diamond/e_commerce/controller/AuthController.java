package com.diamond.e_commerce.controller;

import com.diamond.e_commerce.dto.RegisterRequest;
import com.diamond.e_commerce.dto.UserResponse;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.diamond.e_commerce.dto.LoginRequest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService; // Inject AuthService

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
    // Delegate registration logic to the service
    ApiResponse<UserResponse> response = authService.register(request);
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<UserResponse>> login(@Valid @RequestBody LoginRequest request) {
    ApiResponse<UserResponse> response = authService.login(request);
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
  }
}
