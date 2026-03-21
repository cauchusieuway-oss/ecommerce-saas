package com.vanson.EcommerceSaaS.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ImageService {
    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    public String upload(MultipartFile file) {
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), Map.of());
            return result.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Upload failed");
        }
    }
}