package com.diamond.e_commerce.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "stock_qty", nullable = false)
  private Integer stockQty;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDate createdAt;

  @Column(name = "created_by", nullable = false, updatable = false)
  private Long createdBy;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDate.now();
  }
}
