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
    private Long id;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

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

    public OrderDetail(Long price, String name, Long quantity, String color,
                       Member member, ProductSku productSku, Order order) {
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.color = color;
        this.member = member;
        this.productSku = productSku;
        this.order = order;
    }
}
