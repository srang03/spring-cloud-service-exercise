package org.example.catalogservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogDto implements Serializable {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;

    private String orderId;
    private String userId;
}
