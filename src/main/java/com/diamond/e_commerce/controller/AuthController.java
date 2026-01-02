package com.diamond.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diamond.e_commerce.dto.LoginRequest;
import com.diamond.e_commerce.dto.RegisterRequest;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.response.AuthTokenResponse;
import com.diamond.e_commerce.response.UserResponse;
import com.diamond.e_commerce.service.interfe.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService; // Inject AuthService

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
    ApiResponse<UserResponse> response = authService.register(request);
    log.info("Registration {} for username/email={}",
        response.getStatusCode() == 201 ? "successful" : "failed",
        request.getEmail());
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthTokenResponse>> login(@Valid @RequestBody LoginRequest request) {

    log.info("Login attempt for username/email={}", request.getEmail());
    ApiResponse<AuthTokenResponse> response = authService.login(request);
    log.info("Login {} for username/email={}",
        response.getStatusCode() == 200 ? "successful" : "failed",
        request.getEmail());
    return new ResponseEntity<>(response,
        HttpStatus.valueOf(response.getStatusCode()));
  }

}
