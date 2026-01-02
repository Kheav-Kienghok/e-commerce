package com.diamond.e_commerce.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.diamond.e_commerce.dto.CreateProductRequest;
import com.diamond.e_commerce.dto.UpdateProductRequest;
import com.diamond.e_commerce.entity.Product;
import com.diamond.e_commerce.entity.User;
import com.diamond.e_commerce.repository.ProductRepository;
import com.diamond.e_commerce.repository.UserRepository;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.response.PageResponse;
import com.diamond.e_commerce.response.ProductResponse;
import com.diamond.e_commerce.security.AuthUser;
import com.diamond.e_commerce.service.interfe.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  private Long getCurrentUserId() {
    AuthUser authUser = (AuthUser) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
    return authUser.getUser().getId();
  }

  @Override
  public ApiResponse<ProductResponse> create(CreateProductRequest request) {

    Long userId = getCurrentUserId();
    if (userId == null) {
      return ApiResponse.error(401, "Unauthorized");
    }

    // Load full User entity
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Product product = Product.builder()
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .stockQty(request.getStockQty())
        .createdByUser(user)
        .build();

    productRepository.save(product);
    log.info("Product created successfully: id={}, name='{}'", product.getId(), product.getName());

    ProductResponse response = ProductResponse.fromEntity(product);
    return ApiResponse.success(201, "Product created successfully", response);
  }

  @Override
  public ApiResponse<ProductResponse> update(Long id, UpdateProductRequest request) {

    Product product = productRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("Product not found for update: id={}", id);
          return new RuntimeException("Product not found");
        });

    if (request.getName() != null)
      product.setName(request.getName());

    if (request.getDescription() != null)
      product.setDescription(request.getDescription());

    if (request.getPrice() != null)
      product.setPrice(request.getPrice());

    if (request.getStockQty() != null)
      product.setStockQty(request.getStockQty());

    if (request.getImageUrl() != null)
      product.setImageUrl(request.getImageUrl());

    productRepository.save(product);
    log.info("Product updated successfully: id={}, name='{}'", product.getId(), product.getName());

    // Return DTO instead of entity
    ProductResponse response = ProductResponse.fromEntity(product);
    return ApiResponse.success(200, "Product updated successfully", response);
  }

  @Override
  public ApiResponse<Void> delete(Long id) {

    Optional<Product> optionalProduct = productRepository.findById(id);

    if (optionalProduct.isEmpty()) {
      return ApiResponse.error(404, "Product not found");
    }

    Product product = optionalProduct.get();
    Long userId = getCurrentUserId();

    // Manual ownership check
    if (!product.getCreatedByUser().getId().equals(userId)) {
      log.warn("User {} is not allowed to delete product id={}", userId, id);
      return ApiResponse.error(403, "You are not allowed to delete this product");
    }

    productRepository.delete(product);
    log.info("Product deleted successfully: id={}", id);

    return ApiResponse.success(200, "Product deleted successfully");
  }

  @Override
  public ApiResponse<PageResponse<ProductResponse>> findAll(Pageable pageable) {

    Page<Product> page = productRepository.findAll(pageable);

    var items = page.getContent()
        .stream()
        .map(ProductResponse::fromEntity)
        .collect(Collectors.toList());

    PageResponse<ProductResponse> response = new PageResponse<>(
        items,
        new PageResponse.Meta(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.hasNext(),
            page.hasPrevious()));

    log.debug("Products fetched: totalElements={}", page.getTotalElements());
    return ApiResponse.success(200, "Products retrieved successfully", response);
  }

  @Override
  public ApiResponse<PageResponse<ProductResponse>> findByNameContainingIgnoreCase(String name, Pageable pageable) {
    Page<Product> page = productRepository.findByNameContainingIgnoreCase(name, pageable);

    var items = page.getContent()
        .stream()
        .map(ProductResponse::fromEntity)
        .collect(Collectors.toList());

    PageResponse<ProductResponse> response = new PageResponse<>(
        items,
        new PageResponse.Meta(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.hasNext(),
            page.hasPrevious()));

    log.debug("Search result: totalElements={}", page.getTotalElements());
    return ApiResponse.success(200, "Products retrieved successfully", response);
  }

}
