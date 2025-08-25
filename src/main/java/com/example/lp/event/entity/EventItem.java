package com.example.lp.event.entity;

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

    public EventItem(){}

    public EventItem(Event event, Long salePrice, Long quotaPerUser, Long stock, LocalDateTime createdAt) {
        this.event = event;
        this.salePrice = salePrice;
        this.quotaPerUser = quotaPerUser;
        this.stock = stock;
        this.createdAt = createdAt;
    }
}
