package com.example.lp.event.entity;

import com.example.lp.event.dto.Response.EventItemCacheResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "event_item_cache")
public class EventItemCache {

    @Id
    private Long id; // AUTO_INCREMENT PK

    @Column(name = "event_item_id")
    private Long eventItemId;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Long productPrice;

    @Column(name = "product_cover_image_url")
    private String productCoverImageUrl;

    @Column(name = "sale_price")
    private Long salePrice;

    @Column(name = "product_sku_id")
    private Long productSkuId;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "quantity")
    private Long quantity;

    public EventItemCacheResponse toDto() {
        return new EventItemCacheResponse(
                id, eventItemId, eventId, productId, productName,
                productPrice, productCoverImageUrl, salePrice, productSkuId, size, color, quantity
        );
    }
    public Long getId() { return id; }
    public Long getEventItemId() { return eventItemId; }
    public Long getEventId() { return eventId; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Long getProductPrice() { return productPrice; }
    public String getProductCoverImageUrl() { return productCoverImageUrl; }
    public Long getSalePrice() { return salePrice; }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public Long getQuantity() {
        return quantity;
    }
}
