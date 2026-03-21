package com.vanson.EcommerceSaaS.controller;

import com.vanson.EcommerceSaaS.dto.LoginRequest;
import com.vanson.EcommerceSaaS.dto.LoginResponse;
import com.vanson.EcommerceSaaS.entity.User;
import com.vanson.EcommerceSaaS.service.UserService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getEmail(), request.getPassword());

        return new LoginResponse(token);
    }
}