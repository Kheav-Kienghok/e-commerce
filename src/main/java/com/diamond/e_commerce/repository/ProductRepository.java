package com.diamond.e_commerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.diamond.e_commerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

  @Override
  Page<Product> findAll(Pageable pageable);
}
