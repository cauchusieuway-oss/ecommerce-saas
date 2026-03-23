package com.vanson.EcommerceSaaS.repository;

import com.vanson.EcommerceSaaS.entity.Product;
import com.vanson.EcommerceSaaS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByShopId(Long shopId);

    List<Product> findByShop_Owner(User owner);
}