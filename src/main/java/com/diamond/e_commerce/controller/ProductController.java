package com.diamond.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diamond.e_commerce.dto.ProductRequest;
import com.diamond.e_commerce.entity.Product;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<ApiResponse<Product>> create(
      @RequestBody @Valid ProductRequest request) {

    return ResponseEntity.ok(productService.create(request));
  }

  // // UPDATE
  // @PutMapping("/{id}")
  // public ResponseEntity<ApiResponse<Product>> update(
  // @PathVariable Long id,
  // @RequestBody @Valid ProductRequest request) {

  // return ResponseEntity.ok(productService.update(id, request));
  // }

  // // DELETE
  // @DeleteMapping("/{id}")
  // public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
  // return ResponseEntity.ok(productService.delete(id));
  // }

}
