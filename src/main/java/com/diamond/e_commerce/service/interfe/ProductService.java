package com.diamond.e_commerce.service.interfe;

import org.springframework.data.domain.Pageable;

import com.diamond.e_commerce.dto.CreateProductRequest;
import com.diamond.e_commerce.dto.UpdateProductRequest;
import com.diamond.e_commerce.entity.Product;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.response.PageResponse;
import com.diamond.e_commerce.response.ProductResponse;

public interface ProductService {

  /**
   * Create a new product.
   *
   * @param request product creation request
   * @return ApiResponse containing created product
   */
  ApiResponse<Product> create(CreateProductRequest request);

  /**
   * Update an existing product.
   *
   * @param id      product ID
   * @param request updated product data
   * @return ApiResponse containing updated product
   */
  ApiResponse<Product> update(Long id, UpdateProductRequest request);

  /**
   * Delete a product by ID.
   *
   * @param id product ID
   * @return ApiResponse indicating success
   */
  ApiResponse<Void> delete(Long id);

  /**
   * Get a paginated list of all products.
   *
   * @param pageable pagination information
   * @return ApiResponse containing paginated products
   */
  ApiResponse<PageResponse<ProductResponse>> findAll(Pageable pageable);

  /**
   * Search products by name.
   *
   * @param name     product name (partial or full)
   * @param pageable pagination information
   * @return ApiResponse containing paginated products
   */
  ApiResponse<PageResponse<ProductResponse>> findByNameContainingIgnoreCase(String name, Pageable pageable);
}