package org.example.orderservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class KafkaOrderSchema {
    private String type;
    private List<KafkaOrderField> fields;
    private boolean optional;
    private String name;

}
