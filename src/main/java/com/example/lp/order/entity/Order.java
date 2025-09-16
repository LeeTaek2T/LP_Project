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

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "dear_name")
    private String dearName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TossPayment> tossPaymentList;


    public Order(Long totalPrice, String dearName, String phoneNumber, String address, String addressDetail,
                 String postCode, Member member) {
        this.totalPrice = totalPrice;
        this.dearName = dearName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        this.postCode = postCode;
        this.createdAt = OffsetDateTime.now();
        this.state = "결제 진행 중";
        this.member = member;
    }

    public Order(){}

    public Order(Long totalPrice, String address, String postCode, Member member) {
        this.totalPrice = totalPrice;
        this.address = address;
        this.postCode = postCode;
        this.createdAt = createdAt;
        this.state = state;
        this.member = member;
    }

    public Order(Long id, Member member, Long totalPrice, String address, String postCode,
                 OffsetDateTime createdAt, String state){
        this.id = id;
        this.member = member;
        this.totalPrice = totalPrice;
        this.address = address;
        this.postCode = postCode;
        this.createdAt = createdAt;
        this.state = state;
    }

    public Order(Long id,Member member, Long totalPrice, String address, String postCode,
                 OffsetDateTime createdAt, String state, String cancelReason){
        this.id = id;
        this.member = member;
        this.totalPrice = totalPrice;
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

    public Long getTotalPrice() {
        return totalPrice;
    }

    public String getState() {
        return state;
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