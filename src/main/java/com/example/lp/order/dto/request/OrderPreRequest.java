package com.example.lp.order.dto.request;

public record OrderPreRequest(String tossOrderId,
                              Long totalAmount,
                              String tossPaymentKey){
}
