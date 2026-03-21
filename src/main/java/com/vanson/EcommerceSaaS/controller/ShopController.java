package com.vanson.EcommerceSaaS.controller;

import com.vanson.EcommerceSaaS.entity.Shop;
import com.vanson.EcommerceSaaS.security.JwtAuthenticationToken;
import com.vanson.EcommerceSaaS.service.ShopService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public Shop createShop(@RequestParam String name,
                           @AuthenticationPrincipal String email) {
        return shopService.createShop(name, email);
    }

    @GetMapping
    public List<Shop> getMyShops(
            @AuthenticationPrincipal String email) {
        return shopService.getMyShops(email);
    }
}