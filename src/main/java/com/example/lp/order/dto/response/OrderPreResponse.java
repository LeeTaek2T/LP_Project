package com.example.lp.order.dto.response;

public record OrderPreResponse (Long orderId,
                                String tossOrderId,
                               Long totalAmount,
                               String tossPaymentKey){
}
