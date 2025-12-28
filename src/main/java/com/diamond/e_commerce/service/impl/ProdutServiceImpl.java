package com.diamond.e_commerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.diamond.e_commerce.dto.ProductRequest;
import com.diamond.e_commerce.entity.Product;
import com.diamond.e_commerce.repository.ProductRepository;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.security.AuthUser;
import com.diamond.e_commerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public ApiResponse<Product> create(ProductRequest request) {

    // Extract the current authenticated user
    AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = authUser.getUser().getId(); // Get ID from JWT-backed user

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
  public ApiResponse<Product> update(Long id, ProductRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public ApiResponse<Void> delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public ApiResponse<Page<Product>> findAll(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public ApiResponse<Page<Product>> findByName(String name, Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByName'");
  }

}
