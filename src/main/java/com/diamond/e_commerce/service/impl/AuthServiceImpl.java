package com.diamond.e_commerce.service.impl;

import java.util.Map;

import com.diamond.e_commerce.dto.LoginRequest;
import com.diamond.e_commerce.dto.RegisterRequest;
import com.diamond.e_commerce.dto.UserResponse;
import com.diamond.e_commerce.entity.User;
import com.diamond.e_commerce.enums.RoleEnum;
import com.diamond.e_commerce.repository.UserRepository;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.diamond.e_commerce.security.JwtUtils;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired  
  private PasswordEncoder passwordEncoder; // for hashing passwords

  @Autowired
  private JwtUtils jwtUtils; // Inject JwtUtils

  @Override
  public ApiResponse<UserResponse> register(RegisterRequest request) {

    // Check if email or username already exists
    if (userRepository.existsByEmail(request.getEmail())) {
      return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Email already exists");
    }

    User user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(RoleEnum.USER) // Set default role to USER
        .build();

    userRepository.save(user);

    UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    return ApiResponse.success(HttpStatus.CREATED.value(), "User registered successfully", userResponse);
  }

  @Override
  public ApiResponse<Map<String, String>> login(LoginRequest request) {

    Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
    if (optionalUser.isEmpty()) {
      return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Invalid email or password");
    }

    User user = optionalUser.get();
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Invalid email or password");
    }

    // Geenrate JWT Token
    String token = jwtUtils.generateToken(user);

    // Return token as key=value
    Map<String, String> tokenMap = Map.of("token", token);

    return ApiResponse.success(HttpStatus.OK.value(), "Login successful", tokenMap);
  }
}
