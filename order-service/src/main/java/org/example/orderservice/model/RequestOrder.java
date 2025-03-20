package org.example.orderservice.model;

import lombok.Data;

@Data
public class RequestOrder {
    private String productId;
    private int qty;
    private int unitPrice;
}
