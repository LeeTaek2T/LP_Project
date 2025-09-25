package com.example.lp.order.dto.response;

import com.example.lp.order.dto.request.OrderProductInfo;

import java.util.List;

public record OrderResponse(
        Long orderId,
        Long memberId,
        Long totalPrice,
        String address,
        String addressDetail,
        String postcode,
        String dearName,
        String phoneNumber,
        List<OrderProductInfo> orderProductInfoList
) {
}
