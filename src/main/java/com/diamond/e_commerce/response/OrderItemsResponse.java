package com.diamond.e_commerce.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemsResponse {
  private Long productId;
  private Integer quantity;
  private BigDecimal priceAtBuy; // optional for response
}
