package com.order.ecom.dto;

import lombok.Data;

@Data
public class CartItemRequest {

    private String productId;
    private Integer quantity;
}
