package com.example.lp.cart.entity;

import com.example.lp.member.entity.Member;
import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_sku_id", nullable = false)
    private ProductSku productSku;

    public Cart(){}

    public Cart(Member member, Long quantity, ProductSku productSku) {
        this.member = member;
        this.quantity = quantity;
        this.productSku = productSku;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public Long getQuantity() {
        return quantity;
    }
}
