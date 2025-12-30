package com.diamond.e_commerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateProductRequest {

  private String name;
  private String description;

  @Positive(message = "Price must be positive")
  private BigDecimal price;

  @PositiveOrZero(message = "Stock quantity cannot be negative")
  private Integer stockQty;

  private String imageUrl;
}
