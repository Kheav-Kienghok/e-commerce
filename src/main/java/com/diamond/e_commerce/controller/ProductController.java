package com.diamond.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diamond.e_commerce.dto.CreateProductRequest;
import com.diamond.e_commerce.dto.UpdateProductRequest;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.response.PageResponse;
import com.diamond.e_commerce.response.ProductResponse;
import com.diamond.e_commerce.service.interfe.ProductService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<ApiResponse<ProductResponse>> create(
      @RequestBody @Valid CreateProductRequest request) {

    ApiResponse<ProductResponse> response = productService.create(request);
    log.info("Product created with id={}", response.getData().getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ProductResponse>> update(
      @PathVariable Long id,
      @RequestBody @Valid UpdateProductRequest request) {

    log.info("Updating product id={}", id);
    ApiResponse<ProductResponse> response = productService.update(id, request);
    log.info("Product updated id={}", response.getData().getId());
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

    log.info("Deleting product id={}", id);
    ApiResponse<Void> response = productService.delete(id);
    log.info("Product deleted id={}", id);
    return ResponseEntity.ok(response);
  }

  @GetMapping()
  public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> findAll(
      @PageableDefault(size = 10, sort = "id") Pageable pageable) {

    log.debug("Fetching products page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
    ApiResponse<PageResponse<ProductResponse>> response = productService.findAll(pageable);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/search")
  public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> findByName(
      @RequestParam String name,
      @PageableDefault(size = 10) Pageable pageable) {

    log.debug("Searching products with name containing '{}', page={}, size={}", name,
        pageable.getPageNumber(), pageable.getPageSize());
    ApiResponse<PageResponse<ProductResponse>> response = productService.findByNameContainingIgnoreCase(name, pageable);
    return ResponseEntity.ok(response);
  }

}
