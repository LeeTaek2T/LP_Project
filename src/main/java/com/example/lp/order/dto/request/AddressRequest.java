package com.example.lp.order.dto.request;

public record AddressRequest(
        Long orderId,
        String address,
        String addressDetail,
        String postcode,
        String dearName,
        String phoneNumber) {
}
