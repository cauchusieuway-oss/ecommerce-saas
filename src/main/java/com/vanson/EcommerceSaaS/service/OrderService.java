package com.vanson.EcommerceSaaS.service;

import com.vanson.EcommerceSaaS.dto.CheckoutRequest;
import com.vanson.EcommerceSaaS.entity.Order;
import com.vanson.EcommerceSaaS.entity.OrderItem;
import com.vanson.EcommerceSaaS.entity.Product;
import com.vanson.EcommerceSaaS.entity.User;
import com.vanson.EcommerceSaaS.repository.OrderRepository;
import com.vanson.EcommerceSaaS.repository.ProductRepository;
import com.vanson.EcommerceSaaS.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private OrderService(OrderRepository orderRepository,
                         ProductRepository productRepository,
                         UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Order checkout(CheckoutRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CheckoutRequest.Item item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            double price = product.getPrice() * item.getQuantity();

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(price)
                    .build();

            total += price;
            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .user(user)
                .total(total)
                .items(orderItems)
                .build();

        //gán lại order cho item
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        return orderRepository.save(order);
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
}