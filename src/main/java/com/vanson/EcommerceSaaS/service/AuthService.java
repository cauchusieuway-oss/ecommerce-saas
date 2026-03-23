package com.vanson.EcommerceSaaS.service;

import com.vanson.EcommerceSaaS.entity.Shop;
import com.vanson.EcommerceSaaS.entity.User;
import com.vanson.EcommerceSaaS.repository.ShopRepository;
import com.vanson.EcommerceSaaS.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       ShopRepository shopRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Register
    public User register(User user) {
        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //save user
        User savedUser = userRepository.save(user);

        //tạo shop tự động
        Shop shop = new Shop();
        shop.setName("My Shop");
        shop.setOwner(savedUser);

        shopRepository.save(shop);

        return savedUser;
    }
}