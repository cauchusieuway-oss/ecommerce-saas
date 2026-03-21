package com.vanson.EcommerceSaaS.service;

import com.vanson.EcommerceSaaS.entity.Product;
import com.vanson.EcommerceSaaS.entity.Shop;
import com.vanson.EcommerceSaaS.entity.User;
import com.vanson.EcommerceSaaS.repository.ProductRepository;
import com.vanson.EcommerceSaaS.repository.ShopRepository;
import com.vanson.EcommerceSaaS.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository,
                          ShopRepository shopRepository,
                          UserRepository userRepository) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    public Product createProduct(String name, Double price, String imageUrl,
                                 Long shopId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        //Check quan trọng: shop phải thuốc user
        if (!shop.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You are not owner of this shop");
        }

        Product product = Product.builder()
                .name(name)
                .price(price)
                .imagesUrl(imageUrl)
                .shop(shop)
                .build();

        return productRepository.save(product);
    }

    public List<Product> getProductsByShop(Long shopId) {
        return productRepository.findByShopId(shopId);
    }
}