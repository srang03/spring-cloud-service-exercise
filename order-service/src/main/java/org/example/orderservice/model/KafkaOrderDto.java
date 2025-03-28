package org.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {
    private KafkaOrderSchema schema;
    private KafkaOrderPayload payload;
}
