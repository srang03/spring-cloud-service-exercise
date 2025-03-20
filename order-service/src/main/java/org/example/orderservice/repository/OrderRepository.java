package org.example.orderservice.repository;

import org.example.orderservice.model.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    Iterable<OrderEntity> findByUserId(String userId);
    OrderEntity findByOrderId(String orderId);
}
