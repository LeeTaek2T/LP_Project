package com.example.lp.tosspayment.dto.request;

public record PaymentCancelRequest(Long orderId,
                                   String cancelReason) {
}
