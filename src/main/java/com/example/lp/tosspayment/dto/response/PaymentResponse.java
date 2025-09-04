package com.example.lp.tosspayment.dto.response;

import com.example.lp.order.enums.TossPaymentStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record PaymentResponse(
        Long paymentId,          // 토스 결제 id (PK)
        String tossOrderId,      // 주문번호
        String tossPaymentKey,   // 토스 paymentKey
        String tossPaymentMethod,// 결제수단 (enum 매핑 가능)
        TossPaymentStatus tossPaymentStatus,// 결제상태 (enum 매핑 가능)
        LocalDateTime requestedAt, // 요청시각
        OffsetDateTime approvedAt,  // 승인시각 (NULL 허용)
        Long totalAmount,        // 총 결제금액
        Long orderId            // 주문 id (FK)
) {}