package com.example.lp.order.dto.response;

import java.util.List;

public record OrderCancelPendingResponse(
        Long orderId,
        List<OrderDetailResponse> OrerDetailResponseList,
        Long refundPrice,
        String cancelReason) {
}
