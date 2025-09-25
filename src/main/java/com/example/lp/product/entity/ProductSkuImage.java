package com.example.lp.product.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_sku_image")
public class ProductSkuImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_sku_image_url")
    private String productSkuImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_sku_id", nullable = false)
    private ProductSku productSku;


    public ProductSkuImage() {}

    public ProductSkuImage(String productSkuImageUrl, ProductSku productSku) {
        this.productSkuImageUrl = productSkuImageUrl;
        this.productSku = productSku;
    }

    public Long getId() {
        return id;
    }

    public String getProductSkuImageUrl() {
        return productSkuImageUrl;
    }
}
