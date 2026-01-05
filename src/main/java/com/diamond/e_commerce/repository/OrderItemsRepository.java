package com.diamond.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diamond.e_commerce.entity.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
  List<OrderItems> findByOrderId(Long orderId);
}
