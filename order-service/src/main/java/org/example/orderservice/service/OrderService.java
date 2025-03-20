package org.example.orderservice.service;

import org.example.orderservice.model.OrderDto;

public interface OrderService {
    OrderDto  retrieveOrderByOrderId(String orderId);
    Iterable<OrderDto> retrieveOrderByUserId(String userId);
    OrderDto createOrder(OrderDto orderDto);
}
