package org.example.orderservice.controller;

import org.example.orderservice.messagequeue.KafkaOrdersProducer;
import org.example.orderservice.messagequeue.KafkaProducer;
import org.example.orderservice.model.OrderDto;
import org.example.orderservice.model.RequestOrder;
import org.example.orderservice.model.ResponseOrder;
import org.example.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/order-service")
public class OrderController {
    private final OrderService orderService;
    private KafkaProducer kafkaProducer;
    private KafkaOrdersProducer kafkaOrdersProducer;

    @Autowired
    public OrderController(OrderService orderService, KafkaProducer kafkaProducer, KafkaOrdersProducer kafkaOrdersProducer) {
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
        this.kafkaOrdersProducer = kafkaOrdersProducer;
    }

    @PostMapping("/{user_id}/orders")
    public ResponseEntity<ResponseOrder> createOrder(
            @PathVariable("user_id") String userId,
            @RequestBody RequestOrder requestOrder) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // jpa 작업
        OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);


//        orderService.createOrder(orderDto);
//        ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);

        // send this order the kafka
        // Service 로직
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);

        kafkaProducer.send("example-catalog-topic", orderDto);
        kafkaOrdersProducer.send("orders", orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseOrder> findOrderByOrderId(@PathVariable String orderId) {
        OrderDto orderDto = orderService.retrieveOrderByOrderId(orderId);
        ModelMapper modelMapper = new ModelMapper();
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(orderDto, ResponseOrder.class));
    }

    @GetMapping("{userId}/orders")
    public ResponseEntity<Iterable<ResponseOrder>> findOrderByUserId(@PathVariable String userId) {
        Iterable<OrderDto> orderDtos = orderService.retrieveOrderByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();
        ArrayList<ResponseOrder> orders = new ArrayList<>();
        for (OrderDto orderDto : orderDtos) {
            ResponseOrder order = modelMapper.map(orderDto, ResponseOrder.class);
            orders.add(order);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
