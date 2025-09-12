package com.example.lp.order.dto.request;

import java.util.List;

public record OrderRequest(
        String address,
        String addressDetail,
        String postcode,
        String dearName,
        String phoneNumber,
        Long totalPrice,
        List<OrderProductInfo> orderProductInfoList) {}
