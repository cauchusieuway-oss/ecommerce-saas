package com.vanson.EcommerceSaaS.controller;

import com.vanson.EcommerceSaaS.dto.CheckoutRequest;
import com.vanson.EcommerceSaaS.entity.Order;
import com.vanson.EcommerceSaaS.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    private Order checkout(@RequestBody CheckoutRequest request,
                           @AuthenticationPrincipal String email){
        return orderService.checkout(request,email);
    }

    @GetMapping
    public List<Order> getMyOrders(@AuthenticationPrincipal String email){

        return orderService.getMyOrders(email);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.findById(id);
    }
}