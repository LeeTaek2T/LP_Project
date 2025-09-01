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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_sku_id", nullable = false)
    private ProductSku productSku;

    public Cart(){}

    public Cart(Member member, ProductSku productSku) {
        this.member = member;
        this.productSku = productSku;
    }

    public ProductSku getProductSku() {
        return productSku;
    }
}
