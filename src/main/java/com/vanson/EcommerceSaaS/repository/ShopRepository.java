package com.vanson.EcommerceSaaS.repository;

import com.vanson.EcommerceSaaS.entity.Shop;
import com.vanson.EcommerceSaaS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findByOwnerId(User owner);
}