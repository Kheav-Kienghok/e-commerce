package com.diamond.e_commerce.service.interfe;

import java.util.List;

import com.diamond.e_commerce.entity.OrderItems;

public interface OrderItemsService {

  // Save a single OrderItems entity
  boolean save(OrderItems item);

  // Save multiple OrderItems entities
  boolean saveAll(List<OrderItems> items);

  // Get all items for a given order ID
  List<OrderItems> getItemsByOrderId(Long orderId);
}
