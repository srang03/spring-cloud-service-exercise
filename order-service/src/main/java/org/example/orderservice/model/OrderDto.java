package org.example.orderservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderDto implements Serializable {
        private String productId;
        private Integer qty;
        private Integer unitPrice;
        private Integer totalPrice;

        private String orderId;
        private String userId;
}
