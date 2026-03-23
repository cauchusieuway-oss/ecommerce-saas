package com.vanson.EcommerceSaaS.repository;

import com.vanson.EcommerceSaaS.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    //Lấy tất cả item theo order
    List<OrderItem> findByOrderId(Long id);
}