package org.example.orderservice.service;

import com.netflix.discovery.converters.Auto;
import org.example.orderservice.model.OrderDto;
import org.example.orderservice.model.OrderEntity;
import org.example.orderservice.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto retrieveOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public Iterable<OrderDto> retrieveOrderByUserId(String userId) {
        Iterable<OrderEntity> orderEntities = orderRepository.findByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            OrderDto orderDto = modelMapper.map(orderEntity, OrderDto.class);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
        orderEntity.setCreatedAt(new Date());
        orderRepository.save(orderEntity);
        return orderDto;
    }
}
