package com.example.lp.product.dto.response;

public record ProductSkuResponse(Long productSkuId,
                                 Long productId,
                                String skuCode,
                                String color,
                                String size,
                                int stock,
                                int price,
                                boolean inActive) {}
