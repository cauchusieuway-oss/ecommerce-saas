package com.vanson.EcommerceSaaS.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //Chủ Shop
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}