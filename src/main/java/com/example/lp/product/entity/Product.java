package com.example.lp.product.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    @Column(name="name", length = 255, nullable = false)
    private String name;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSku> productSkuList = new ArrayList<>();

    public Product(){}

    public Product(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public Long getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }
}
