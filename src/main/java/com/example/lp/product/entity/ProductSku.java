package com.example.lp.product.entity;

import com.example.lp.cart.entity.Cart;
import com.example.lp.event.entity.EventItem;
import com.example.lp.order.entity.OrderDetail;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "productSku")
public class ProductSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_sku_id")
    private Long id;

    @Column(name = "sku_code", length = 100)
    private String productSkuCode;

    @Column(name = "color", length = 5, nullable = false)
    private String color;

    @Column(name = "size", length = 5, nullable = false)
    private String size;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productSku", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartList;

    @OneToMany(mappedBy = "productSku", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetailList;

    @OneToMany(mappedBy = "productSku", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSkuImage> productSkuImagesList;

    public ProductSku(){}

    public ProductSku(String color, String size, Long quantity, Product product) {
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.state = "재고 있음";
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
        this.product = product;
    }

    public ProductSku(Product product, String productSkuCode, String color, String size, Long quantity,
                      OffsetDateTime createdAt, OffsetDateTime updatedAt){
        this.product = product;
        this.productSkuCode = productSkuCode;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.state = "";
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getState() {
        return state;
    }

    public Product getProduct() {
        return product;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void reduceAmount(Long amount){
        this.quantity = this.quantity - amount;
    }

    public void plusAmount(Long amount) {
        this.quantity = this.quantity + amount;
    }
}
