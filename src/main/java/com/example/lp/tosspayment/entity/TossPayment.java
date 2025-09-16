package com.example.lp.tosspayment.entity;

import com.example.lp.order.entity.Order;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tossPayment")
public class TossPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "toss_order_id")
    private String tossOrderId;

    @Column(name = "toss_payment_key")
    private String tossPaymentKey;

    @Column(name = "toss_payment_method")
    private String tossPaymentMethod;

    @Column(name = "toss_payment_status")
    private String tossPaymentStatus;

    @Column(name = "reqeusted_at")
    private OffsetDateTime reqeustedAt;

    @Column(name = "approved_at")
    private OffsetDateTime approvedAt;

    @Column(name = "total_amount")
    private Long totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public TossPayment(){}

    public TossPayment(String tossOrderId, Long totalAmount, OffsetDateTime reqeustedAt, Order order){
        this.tossOrderId = tossOrderId;
        this.totalAmount = totalAmount;
        this.reqeustedAt = reqeustedAt;
        this.order = order;
    }

    public TossPayment(Long id, String tossOrderId, String tossPaymentKey, String tossPaymentMethod,
                       String tossPaymentStatus, OffsetDateTime reqeustedAt,
                       OffsetDateTime approvedAt, Long totalAmount, Order order) {
        this.id = id;
        this.tossOrderId = tossOrderId;
        this.tossPaymentKey = tossPaymentKey;
        this.tossPaymentMethod = tossPaymentMethod;
        this.tossPaymentStatus = tossPaymentStatus;
        this.reqeustedAt = reqeustedAt;
        this.approvedAt = approvedAt;
        this.totalAmount = totalAmount;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public String getTossPaymentKey() {
        return tossPaymentKey;
    }

    public String getTossOrderId() {
        return tossOrderId;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public String getTossPaymentMethod() {
        return this.tossPaymentMethod;
    }

    public String getTossPaymentStatus() {
        return tossPaymentStatus;
    }

    public OffsetDateTime getReqeustedAt() {
        return reqeustedAt;
    }

    public OffsetDateTime getApprovedAt() {
        return approvedAt;
    }

    public Order getOrder() {
        return order;
    }

    public void changeStatus(String status){
        this.tossPaymentStatus = status;
    }
}
