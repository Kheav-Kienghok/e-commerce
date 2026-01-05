package com.diamond.e_commerce.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.diamond.e_commerce.enums.StatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderReponse {
  private Long id;
  private StatusEnum status;
  private BigDecimal totalPrice;
  private LocalDateTime createdAt;
  private Long userId;
  private List<OrderItemsResponse> items;
}
