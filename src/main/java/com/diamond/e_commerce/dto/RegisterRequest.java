package com.diamond.e_commerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank(message = "Username is required")
  @Size(min = 3, message = "Username must be at least 3 characters")
  private String username;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 6, message = "Password must be at least 6 characters")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+$", message = "Password must contain at least one lowercase and one uppercase letter")
  private String password;
}
