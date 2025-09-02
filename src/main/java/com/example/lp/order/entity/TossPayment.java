package com.example.lp.order.entity;

import com.example.lp.order.enums.TossPaymentMethod;
import com.example.lp.order.enums.TossPaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "toss_payment_method")
    private TossPaymentMethod tossPaymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "toss_payment_status")
    private TossPaymentStatus tossPaymentStatus;

    @Column(name = "reqeusted_at")
    private LocalDateTime reqeustedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "total_amount")
    private Long totalAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public TossPayment(){}

    public TossPayment(String tossOrderId, Long totalAmount, String tossPaymentKey){
        this.tossOrderId = tossOrderId;
        this.totalAmount = totalAmount;
        this.tossPaymentKey = tossPaymentKey;
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
}
