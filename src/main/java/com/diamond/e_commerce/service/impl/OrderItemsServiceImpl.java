package com.diamond.e_commerce.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diamond.e_commerce.entity.OrderItems;
import com.diamond.e_commerce.repository.OrderItemsRepository;
import com.diamond.e_commerce.service.interfe.OrderItemsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemsServiceImpl implements OrderItemsService {

  private final OrderItemsRepository orderItemsRepository;

  @Override
  public boolean save(OrderItems item) {
    orderItemsRepository.save(item);
    return true;
  }

  @Override
  public boolean saveAll(List<OrderItems> items) {
    if (items == null || items.isEmpty()) {
      return false;
    }
    orderItemsRepository.saveAll(items);
    return true;
  }

  @Override
  public List<OrderItems> getItemsByOrderId(Long orderId) {
    if (orderId == null) {
      throw new IllegalArgumentException("Order ID cannot be null");
    }
    return orderItemsRepository.findByOrderId(orderId);
  
  }

}
