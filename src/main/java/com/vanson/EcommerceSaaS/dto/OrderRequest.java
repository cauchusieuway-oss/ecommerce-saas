package com.vanson.EcommerceSaaS.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long shopId;
    private List<OrderItemRequest> items;
}