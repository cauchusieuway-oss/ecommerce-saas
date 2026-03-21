package com.vanson.EcommerceSaaS.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckoutRequest {
    private List<Item> items;

    @Data
    public static class Item {
        private Long productId;
        private int quantity;
    }
}