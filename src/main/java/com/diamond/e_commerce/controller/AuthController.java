package com.diamond.e_commerce.controller;

import java.util.Map;

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
import com.diamond.e_commerce.response.UserResponse;
import com.diamond.e_commerce.service.interfe.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService; // Inject AuthService


  @PostMapping("/register")
  public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
    // Delegate registration logic to the service
    ApiResponse<UserResponse> response = authService.register(request);
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequest request) {
    ApiResponse<Map<String,String>> response = authService.login(request);
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
  }
}
