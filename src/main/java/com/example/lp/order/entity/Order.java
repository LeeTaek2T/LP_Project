package com.example.lp.order.entity;

import com.example.lp.member.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_entity")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "address")
    private String address;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetailList;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private TossPayment tossPayment;

    public Order(){}

    public Order(Member member, Long totalAmount, String address, String postCode) {
        this.member = member;
        this.totalAmount = totalAmount;
        this.address = address;
        this.postCode = postCode;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }
}