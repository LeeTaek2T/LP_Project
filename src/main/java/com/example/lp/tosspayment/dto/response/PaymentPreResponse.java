package com.example.lp.tosspayment.dto.response;

public record PaymentPreResponse(Long paymentId,
                                 String tossOrderId,
                                 Long totalAmount,
                                 Long orderId){
}
