package com.vanson.EcommerceSaaS.controller;

import com.vanson.EcommerceSaaS.dto.CheckoutRequest;
import com.vanson.EcommerceSaaS.dto.OrderItemRequest;
import com.vanson.EcommerceSaaS.dto.OrderRequest;
import com.vanson.EcommerceSaaS.entity.Order;
import com.vanson.EcommerceSaaS.entity.OrderItem;
import com.vanson.EcommerceSaaS.entity.Product;
import com.vanson.EcommerceSaaS.repository.OrderItemRepository;
import com.vanson.EcommerceSaaS.repository.OrderRepository;
import com.vanson.EcommerceSaaS.repository.ProductRepository;
import com.vanson.EcommerceSaaS.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;


    public OrderController(OrderService orderService, OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getMyOrders(@AuthenticationPrincipal String email){

        return orderService.getMyOrders(email);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @Transactional
    @PostMapping("/checkout")
    public Order checkout(@RequestBody CheckoutRequest request,
                          @AuthenticationPrincipal String email) {
        return orderService.checkout(request, email);
    }
}