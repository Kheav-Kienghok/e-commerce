package com.diamond.e_commerce.service.impl;

import java.math.BigDecimal;

import com.diamond.e_commerce.dto.OrderRequest;
import com.diamond.e_commerce.entity.Order;
import com.diamond.e_commerce.entity.Product;
import com.diamond.e_commerce.entity.User;
import com.diamond.e_commerce.enums.StatusEnum;
import com.diamond.e_commerce.repository.OrderRepository;
import com.diamond.e_commerce.repository.ProductRepository;
import com.diamond.e_commerce.repository.UserRepository;
import com.diamond.e_commerce.response.ApiResponse;
import com.diamond.e_commerce.response.OrderReponse;
import com.diamond.e_commerce.service.interfe.OrderItemsService;
import com.diamond.e_commerce.service.interfe.OrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import com.diamond.e_commerce.entity.OrderItems;
import com.diamond.e_commerce.response.OrderItemsResponse;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemsService orderItemService;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  @Transactional
  @Override
  public ApiResponse<OrderReponse> createOrder(OrderRequest request) {

    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    // 2. Create order entity
    Order order = Order.builder()
        .user(user)
        .status(StatusEnum.PENDING)
        .createdAt(LocalDateTime.now())
        .build();

    Order savedOrder = orderRepository.save(order);

    // Map DTOs → Entities
    List<OrderItems> items = request.getItems().stream().map(dto -> {

      Product product = productRepository.findById(dto.getProductId())
          .orElseThrow(() -> new RuntimeException("Product not found"));

      OrderItems item = OrderItems.builder()
          .order(savedOrder)
          .product(product)
          .quantity(dto.getQuantity())
          .priceAtBuy(product.getPrice())
          .build();

      return item;

    }).collect(Collectors.toList());

    // 4. Persist order items
    orderItemService.saveAll(items);

    // 5. Calculate total price
    BigDecimal totalPrice = items.stream()
        .map((OrderItems i) -> i.getPriceAtBuy().multiply(BigDecimal.valueOf(i.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    savedOrder.setTotalPrice(totalPrice);

    orderRepository.save(savedOrder); // update with correct total

    List<OrderItemsResponse> itemResponses = items.stream()
        .map((OrderItems item) -> OrderItemsResponse.builder()
            .productId(item.getProduct().getId())
            .quantity(item.getQuantity())
            .priceAtBuy(item.getPriceAtBuy())
            .build())
        .collect(Collectors.toList());

    OrderReponse response = OrderReponse.builder()
        .id(savedOrder.getId())
        .status(savedOrder.getStatus())
        .createdAt(savedOrder.getCreatedAt())
        .totalPrice(savedOrder.getTotalPrice())
        .userId(user.getId())
        .items(itemResponses)
        .build();

    return ApiResponse.success(HttpStatus.ACCEPTED.value(), "Order created successfully", response);
  }

  @Override
  public ApiResponse<OrderReponse> getOrderById(Long orderId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getOrderById'");
  }

  @Override
  public ApiResponse<List<OrderReponse>> getAllOrders() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
  }
}
