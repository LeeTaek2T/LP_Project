package com.example.lp.product.dto.request;

public record ProductSkuRequest(Long productId,
                                String skuCode,
                                String color,
                                String size,
                                int stock,
                                int price,
                                boolean inActive) {}
