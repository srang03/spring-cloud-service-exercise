package org.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaOrderField {
    private String type;
    private boolean optional;
    private String field;
}
