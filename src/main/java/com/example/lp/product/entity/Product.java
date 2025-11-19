package com.example.lp.product.entity;

import com.example.lp.event.entity.EventItem;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
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

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "cover_image_url", nullable = false)
    private String coverImageUrl;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "is_saled", nullable = false)
    private Boolean isSaled;

    @Column(name="created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSku> productSkuList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<EventItem> eventItemList;

    public Product(){}

    public Product(String name, Long price, String coverImageUrl, String category) {
        this.name = name;
        this.price = price;
        this.coverImageUrl = coverImageUrl;
        this.category = category;
        this.createdAt = OffsetDateTime.now();
        this.isSaled = false;
    }

    public Product(String name, OffsetDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public Long getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getCategory() {
        return category;
    }

    public Boolean getSaled() {
        return isSaled;
    }

    public List<ProductSku> getProductSkuList() {
        return productSkuList;
    }

    public void changeOnIsSaled() {
        this.isSaled = true;
    }
}
