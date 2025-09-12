package com.example.lp.cart.dto.reqeust;

public record CartRequest(Long productId,
                          Long productSkuId,
                          Long quantity) { }
