package com.example.lp.tosspayment.dto.response;

public record PaymentCancelResponse(
        String tossPaymentKey,
        String tossOrderId,
        String tossPaymentStatus,
        Long refundAmount
) {
}
