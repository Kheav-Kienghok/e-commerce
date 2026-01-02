package com.diamond.e_commerce.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.diamond.e_commerce.entity.Product;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({ "id", "name", "description", "price", "stockQty", "imageUrl", "createdAt", "createdByUser" })
public class ProductResponse {

  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer stockQty;
  private String imageUrl;
  private LocalDateTime createdAt;

  // Only show id and username
  private CreatedByUserDTO createdByUser;

  @Data
  @Builder
  @JsonPropertyOrder({ "id", "username" })
  public static class CreatedByUserDTO {
    private Long id;
    private String username;
  }

  public static ProductResponse fromEntity(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .stockQty(product.getStockQty())
        .imageUrl(product.getImageUrl())
        .createdAt(product.getCreatedAt().atStartOfDay())
        .createdByUser(
            CreatedByUserDTO.builder()
                .id(product.getCreatedByUser().getId())
                .username(product.getCreatedByUser().getUsername())
                .build())
        .build();
  }
}