package org.example.orderservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaOrderPayload {
    private String order_id;
    private String user_id;
    private String product_id;
    private int qty;
    private int unit_price;
    private int total_price;
}
