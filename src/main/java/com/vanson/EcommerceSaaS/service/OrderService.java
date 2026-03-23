package com.vanson.EcommerceSaaS.service;

import com.vanson.EcommerceSaaS.dto.CheckoutRequest;
import com.vanson.EcommerceSaaS.dto.OrderItemRequest;
import com.vanson.EcommerceSaaS.entity.*;
import com.vanson.EcommerceSaaS.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final OrderItemRepository orderItemRepository;

    private OrderService(OrderRepository orderRepository,
                         ProductRepository productRepository,
                         UserRepository userRepository, ShopRepository shopRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<Order> getMyOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUserId(user.getId());
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order checkout(CheckoutRequest request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        Shop shop = shopRepository.findByOwnerId(user.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No shop"));
        Order order = new Order();
        order.setShopId(shop.getId());
        order.setTotal(0.0);

        Order saved = orderRepository.save(order);

        double total = 0;

        List<OrderItem> items = new ArrayList<>();

        for (CheckoutRequest.Item item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow();

            OrderItem oi = new OrderItem();
            oi.setOrder(saved);
            oi.setProduct(product);
            oi.setQuantity(item.getQuantity());
            oi.setPrice(product.getPrice());

            total += product.getPrice() * item.getQuantity();

            items.add(oi);
        }

        orderItemRepository.saveAll(items);

        saved.setTotal(total);

        return orderRepository.save(saved);
    }
}