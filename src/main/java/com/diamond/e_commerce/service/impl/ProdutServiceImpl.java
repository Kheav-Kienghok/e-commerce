package com.diamond.e_commerce.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.diamond.e_commerce.dto.CreateProductRequest;
import com.diamond.e_commerce.dto.UpdateProductRequest;
import com.diamond.e_commerce.entity.Product;
import com.diamond.e_commerce.repository.ProductRepository;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.response.PageResponse;
import com.diamond.e_commerce.response.ProductResponse;
import com.diamond.e_commerce.security.AuthUser;
import com.diamond.e_commerce.service.interfe.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private Long getCurrentUserId() {
    AuthUser authUser = (AuthUser) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
    return authUser.getUser().getId();
  }

  @Override
  public ApiResponse<Product> create(CreateProductRequest request) {

    Long userId = getCurrentUserId();

    Product product = Product.builder()
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .stockQty(request.getStockQty())
        .createdBy(userId)
        .build();

    productRepository.save(product);

    return ApiResponse.success(201, "Product created successfully", product);
  }

  @Override
  public ApiResponse<Product> update(Long id, UpdateProductRequest request) {

    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found"));

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

    return ApiResponse.success(200, "Product updated successfully", product);
  }

  @Override
  public ApiResponse<Void> delete(Long id) {

    Optional<Product> optionalProduct = productRepository.findById(id);

    if (optionalProduct.isEmpty()) {
      return ApiResponse.error(404, "Product not found");
    }

    Product product = optionalProduct.get();
    Long userId = getCurrentUserId();

    // Authorization check
    if (!product.getCreatedBy().equals(userId)) {
      return ApiResponse.error(403, "You are not allowed to delete this product");
    }

    productRepository.delete(product);

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

    return ApiResponse.success(200, "Products retrieved successfully", response);
  }

}
