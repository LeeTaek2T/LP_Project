package com.example.lp.order.entity;

import com.example.lp.member.entity.Member;
import com.example.lp.product.entity.ProductSku;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "product_cover_image_url",nullable = false)
    private String productCoverImageUrl;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "state")
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_sku_id", nullable = false)
    private ProductSku productSku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderDetail() {}

    public OrderDetail(Long price, String name, String productCoverImageUrl ,Long quantity, String size, String color,
                       Member member, ProductSku productSku, Order order) {
        this.price = price;
        this.name = name;
        this.productCoverImageUrl = productCoverImageUrl;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.member = member;
        this.productSku = productSku;
        this.order = order;
        this.state = "결제중";
    }

    public Long getId() {
        return id;
    }

    public Long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getColor() {
        return color;
    }

    public Member getMember() {
        return member;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public Order getOrder() {
        return order;
    }

    public String getSize() {
        return size;
    }

    public String getProductCoverImageUrl() {
        return productCoverImageUrl;
    }

    public void changeState(String state) {
        this.state = state;
    }
}
