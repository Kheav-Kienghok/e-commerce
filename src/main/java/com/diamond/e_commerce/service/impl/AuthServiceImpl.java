package com.diamond.e_commerce.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diamond.e_commerce.dto.LoginRequest;
import com.diamond.e_commerce.dto.RegisterRequest;
import com.diamond.e_commerce.entity.User;
import com.diamond.e_commerce.enums.RoleEnum;
import com.diamond.e_commerce.repository.UserRepository;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.response.AuthTokenResponse;
import com.diamond.e_commerce.response.UserResponse;
import com.diamond.e_commerce.security.JwtUtils;
import com.diamond.e_commerce.service.interfe.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
      log.warn("Registration failed: Email already exists '{}'", request.getEmail());
      return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Email already exists");
    }

    User user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(RoleEnum.USER) // Set default role to USER
        .build();

    userRepository.save(user);
    log.info("User registered successfully: id={}, username='{}'", user.getId(), user.getUsername());

    UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    return ApiResponse.success(HttpStatus.CREATED.value(), "User registered successfully", userResponse);
  }

  @Override
  public ApiResponse<AuthTokenResponse> login(LoginRequest request) {

    Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
    if (optionalUser.isEmpty()) {
      log.warn("Login failed: email not found '{}'", request.getEmail());
      return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Invalid email or password");
    }

    User user = optionalUser.get();
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      log.warn("Login failed: incorrect password for email '{}'", request.getEmail());
      return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Invalid email or password");
    }

    // Geenrate JWT Token
    String token = jwtUtils.generateToken(user);
    log.info("Login successful: userId={}, email='{}'", user.getId(), user.getEmail());

    AuthTokenResponse authToken = new AuthTokenResponse(token);
    return ApiResponse.success(HttpStatus.OK.value(), "Login successful", authToken);
  }
}
