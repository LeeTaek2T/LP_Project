package com.example.lp.order.entity;

import com.example.lp.member.entity.Member;
import com.example.lp.tosspayment.entity.TossPayment;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
    private OffsetDateTime createdAt;

    @Column(name = "state")
    private String state;

    @Column(name = "cancel_reason")
    private String cancelReason;

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
        this.createdAt = OffsetDateTime.now();
        this.state = "결제 중";
    }

    public Order(Long id,Member member, Long totalAmount, String address, String postCode,
                 OffsetDateTime createdAt, String state){
        this.id = id;
        this.member = member;
        this.totalAmount = totalAmount;
        this.address = address;
        this.postCode = postCode;
        this.createdAt = createdAt;
        this.state = state;
    }

    public Order(Long id,Member member, Long totalAmount, String address, String postCode,
                 OffsetDateTime createdAt, String state, String cancelReason){
        this.id = id;
        this.member = member;
        this.totalAmount = totalAmount;
        this.address = address;
        this.postCode = postCode;
        this.createdAt = createdAt;
        this.state = state;
        this.cancelReason = cancelReason;
    }



    public Long getId() {
        return this.id;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public String getState() {
        return state;
    }

    public TossPayment getTossPayment() {
        return tossPayment;
    }

    public String getAddress() {
        return address;
    }

    public String getPostCode() {
        return postCode;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public Member getMember() {
        return member;
    }

    public void changeState(String state) {
        this.state = state;
    }
}