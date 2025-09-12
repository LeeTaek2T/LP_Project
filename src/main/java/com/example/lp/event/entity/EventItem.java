package com.example.lp.event.entity;

import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "eventItem")
public class EventItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_item_id")
    Long Id;

    @Column(name = "sale_price", nullable = false)
    Long salePrice;

    @Column(name = "quota_per_user", nullable = false)
    Long quotaPerUser;


    @Column(name = "created_at", nullable = false)
    OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    public EventItem(){}

    public EventItem(Event event, Long salePrice, Long quotaPerUser, Product product) {
        this.event = event;
        this.salePrice = salePrice;
        this.quotaPerUser = quotaPerUser;
        this.createdAt = OffsetDateTime.now();
        this.product = product;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Product getProduct() {
        return product;
    }
}
