package com.vanson.EcommerceSaaS.controller;

import com.vanson.EcommerceSaaS.entity.Product;
import com.vanson.EcommerceSaaS.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    private ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestParam String name,
                                 @RequestParam Double price,
                                 @RequestParam String imageUrl,
                                 @RequestParam Long shopId,
                                 @AuthenticationPrincipal String email) {
        return productService.createProduct(name, price, imageUrl, shopId, email);
    }

    @GetMapping
    public List<Product> getProducts(@RequestParam Long shopId) {
        return productService.getProductsByShop(shopId);
    }
}