package com.example.lp.tosspayment.dto.response;

public record PaymentPreResponse(Long paymentId,
                                 String orderId,
                                 Long totalAmount,
                                 Long dbOrderId){
}
