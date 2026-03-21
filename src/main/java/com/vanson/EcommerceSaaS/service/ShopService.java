package com.vanson.EcommerceSaaS.service;

import com.vanson.EcommerceSaaS.entity.Shop;
import com.vanson.EcommerceSaaS.entity.User;
import com.vanson.EcommerceSaaS.repository.ShopRepository;
import com.vanson.EcommerceSaaS.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ShopService(ShopRepository shopRepository, UserRepository userRepository) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    public Shop createShop(String name, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shop shop = Shop.builder()
                .name(name)
                .owner(user)
                .build();

        return shopRepository.save(shop);
    }

    public List<Shop> getMyShops(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return shopRepository.findByOwnerId(user.getId());
    }
}