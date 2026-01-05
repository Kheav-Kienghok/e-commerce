package com.diamond.e_commerce.service.interfe;

import java.util.List;

import com.diamond.e_commerce.dto.OrderRequest;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.response.OrderReponse;

public interface OrderService {
  // Create a new order
  ApiResponse<OrderReponse> createOrder(OrderRequest request);

  // Get a single order by ID
  ApiResponse<OrderReponse> getOrderById(Long orderId);

  // Get all orders
  ApiResponse<List<OrderReponse>> getAllOrders();
}
