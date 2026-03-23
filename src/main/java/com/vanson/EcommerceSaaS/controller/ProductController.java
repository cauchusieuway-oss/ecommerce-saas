package com.vanson.EcommerceSaaS.controller;

import com.vanson.EcommerceSaaS.entity.Product;
import com.vanson.EcommerceSaaS.entity.Shop;
import com.vanson.EcommerceSaaS.entity.User;
import com.vanson.EcommerceSaaS.repository.ProductRepository;
import com.vanson.EcommerceSaaS.repository.ShopRepository;
import com.vanson.EcommerceSaaS.repository.UserRepository;
import com.vanson.EcommerceSaaS.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    private ProductController(ProductService productService, UserRepository userRepository, ShopRepository shopRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product, Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        //lấy shop của user
        List<Shop> shops = shopRepository.findByOwnerId(user.getId());

        if (shops.isEmpty()) {
            throw new RuntimeException("No shop found");
        }

        Shop shop = shops.get(0);

        //set shop đúng cách
        product.setShop(shop);

        return productRepository.save(product);
    }

    @GetMapping
    public List<Product> getProducts(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        return productRepository.findByShop_Owner(user);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}