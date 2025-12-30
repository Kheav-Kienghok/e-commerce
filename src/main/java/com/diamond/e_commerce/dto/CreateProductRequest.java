package com.diamond.e_commerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateProductRequest {

  @NotBlank(message = "Product name is required")
  private String name;

  @NotBlank(message = "Product description is required")
  private String description;

  @NotNull(message = "Price is required")
  @Positive(message = "Price must be positive")
  private BigDecimal price;

  @NotNull(message = "Stock quantity is required")
  @PositiveOrZero(message = "Stock quantity cannot be negative")
  private Integer stockQty;

  private String imageUrl;
  private Integer createdBy;
}
