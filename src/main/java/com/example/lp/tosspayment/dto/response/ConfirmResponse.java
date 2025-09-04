package com.example.lp.tosspayment.dto.response;

public record ConfirmResponse(String orderId,
                              String paymentKey,
                              Long amount) {
}
