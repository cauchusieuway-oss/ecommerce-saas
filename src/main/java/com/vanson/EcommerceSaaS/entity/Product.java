package com.vanson.EcommerceSaaS.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private String imagesUrl;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}