package com.example.lp.event.entity;

import com.example.lp.product.entity.ProductSku;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventItem")
public class EventItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_item_id")
    Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "sale_price", nullable = false)
    Long salePrice;

    @Column(name = "quota_per_user", nullable = false)
    Long quotaPerUser;

    @Column(name = "stock", nullable = false)
    Long stock;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id", nullable = false)
    ProductSku productSku;

    public EventItem(){}

    public EventItem(Event event, Long salePrice, Long quotaPerUser, Long stock, LocalDateTime createdAt,
                     ProductSku productSku) {
        this.event = event;
        this.salePrice = salePrice;
        this.quotaPerUser = quotaPerUser;
        this.stock = stock;
        this.createdAt = createdAt;
        this.productSku = productSku;
    }

    public Long getId() {
        return Id;
    }

    public Event getEvent() {
        return event;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public Long getQuotaPerUser() {
        return quotaPerUser;
    }

    public Long getStock() {
        return stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ProductSku getProductSku() {
        return productSku;
    }
}
