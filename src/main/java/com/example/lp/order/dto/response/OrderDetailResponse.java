package com.example.lp.order.dto.response;

public record OrderDetailResponse(Long orderDetailId,
                                  Long price,
                                  String name,
                                  Long quantity,
                                  String color,
                                  String size) {
}
