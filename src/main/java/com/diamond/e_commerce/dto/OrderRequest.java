package com.diamond.e_commerce.dto;

import java.math.BigDecimal;
import java.util.List;

import com.diamond.e_commerce.response.OrderItemsResponse;

import lombok.Data;

@Data
public class OrderRequest {

  private Long userId;
  private List<OrderItemsResponse> items;
  private BigDecimal totalPrice; // optional: calculated server-side
}
