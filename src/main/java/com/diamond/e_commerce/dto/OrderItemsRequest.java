package com.diamond.e_commerce.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemsRequest {
  private Long productId; // Product ID in request
  private Integer quantity; // Quantity of this product
  private BigDecimal priceAtBuy; // Optional in response
}
