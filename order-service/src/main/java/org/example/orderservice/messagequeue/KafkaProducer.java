package org.example.orderservice.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.model.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderDto send(String topic, OrderDto orderDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = "";
        try{
            orderJson = objectMapper.writeValueAsString(orderDto);

        }catch (Exception e){
            e.printStackTrace();
        }
        kafkaTemplate.send(topic, orderJson);
        log.info("Kafka Producer sent message: " + orderJson);

        return orderDto;
    }
}
