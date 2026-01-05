package com.diamond.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diamond.e_commerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
  
}
