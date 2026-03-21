package com.vanson.EcommerceSaaS.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
                "cloud_name", "dtysjdozg",
                "api_key", "693649246722339",
                "api_secret", "3JZWJ_4zxFX7Yn7o1xNrl20Yih4"
        ));
    }
}