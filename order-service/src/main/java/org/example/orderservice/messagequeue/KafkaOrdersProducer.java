package org.example.orderservice.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class KafkaOrdersProducer {
    private KafkaTemplate<String, Object> kafkaTemplate;

    List<KafkaOrderField> fields = Arrays.asList(
            new KafkaOrderField("string", true, "order_id"),
            new KafkaOrderField("string", true, "user_id"),
            new KafkaOrderField("string", true, "product_id"),
            new KafkaOrderField("int32", true, "qty"),
            new KafkaOrderField("int32", true, "unit_price"),
            new KafkaOrderField("int32", true, "total_price")
    );

    KafkaOrderSchema schema = KafkaOrderSchema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders").build();

    @Autowired
    public KafkaOrdersProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderDto send(String topic, OrderDto orderDto) {

        KafkaOrderPayload payload = KafkaOrderPayload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = "";
        try {
            // orderJson = objectMapper.writeValueAsString(orderDto);
            orderJson = objectMapper.writeValueAsString(kafkaOrderDto);

        } catch (Exception e) {
            e.printStackTrace();
        }
        kafkaTemplate.send(topic, orderJson);
        log.info("Kafka Producer sent message: " + orderJson);

        return orderDto;
    }
}
