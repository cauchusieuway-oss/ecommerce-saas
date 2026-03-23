package com.vanson.EcommerceSaaS.controller;

import com.vanson.EcommerceSaaS.entity.Shop;
import com.vanson.EcommerceSaaS.entity.User;
import com.vanson.EcommerceSaaS.repository.ShopRepository;
import com.vanson.EcommerceSaaS.repository.UserRepository;
import com.vanson.EcommerceSaaS.service.ShopService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {
    private final ShopService shopService;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public ShopController(ShopService shopService, UserRepository userRepository, ShopRepository shopRepository) {
        this.shopService = shopService;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
    }

    @PostMapping
    public Shop createShop(@RequestBody Shop shop,
                           Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        shop.setOwner(user);
        return shopRepository.save(shop);
    }

    @GetMapping
    public List<Shop> getMyShops(
            Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        return shopRepository.findByOwnerId(user.getId());
    }
}