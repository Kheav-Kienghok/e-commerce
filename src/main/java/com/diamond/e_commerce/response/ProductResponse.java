package com.diamond.e_commerce.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.diamond.e_commerce.entity.Product;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "name", "description", "price", "stockQty", "imageUrl", "createdAt" })
public class ProductResponse {

  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer stockQty;
  private String imageUrl;
  private LocalDateTime createdAt;

  public static ProductResponse fromEntity(Product product) {
    ProductResponse dto = new ProductResponse();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setPrice(product.getPrice());
    dto.setStockQty(product.getStockQty());
    dto.setImageUrl(product.getImageUrl());
    dto.setCreatedAt(product.getCreatedAt().atStartOfDay());
    return dto;
  }
}