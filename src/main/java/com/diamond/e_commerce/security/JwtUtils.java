package com.diamond.e_commerce.security;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.diamond.e_commerce.entity.User;

import lombok.extern.slf4j.Slf4j;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Optional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
@Slf4j
public class JwtUtils {

  private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
  private final Algorithm algorithm;

  public JwtUtils(@Value("${jwt.secret}") String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  /**
   * Generate JWT containing id, username, and email as claims.
   */
  public String generateToken(User user) {
    return JWT.create()
        .withSubject(user.getEmail()) // or email, up to your convention
        .withClaim("id", user.getId())
        .withClaim("username", user.getUsername())
        .withClaim("email", user.getEmail())
        .withClaim("role", user.getRole().name())
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(algorithm);
  }

  public Optional<DecodedJWT> validateTokenSafe(String token) {
    try {
      return Optional.of(JWT.require(algorithm).build().verify(token));
    } catch (JWTVerificationException e) {
      log.warn("JWT invalid: {}", e.getMessage());
      return Optional.empty();
    }
  }

}
