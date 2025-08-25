package com.example.lp.product.entity;

import com.example.lp.event.entity.EventItem;
import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "productSku")
public class ProductSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sku_id")
    private Long id;

    @Column(name = "sku_code", length = 100, nullable = false)
    private String skuCode;

    @Column(name = "color", length = 5, nullable = false)
    private String color;

    @Column(name = "size", length = 5, nullable = false)
    private String size;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "price",nullable = false)
    private int price;

    @Column(name = "in_active", nullable = false)
    private Boolean inActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "eventItem", cascade = CascadeType.ALL, orphanRemoval = true)
    List<EventItem> eventItemList;

    public ProductSku(){}

    public ProductSku(Product product, String skuCode, String color, String size, int stock, int price,
                      Boolean inActive, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.product = product;
        this.skuCode = skuCode;
        this.color = color;
        this.size = size;
        this.stock = stock;
        this.price = price;
        this.inActive = inActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public int getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }

    public Boolean getInActive() {
        return inActive;
    }

    public Product getProduct() {
        return product;
    }
}
