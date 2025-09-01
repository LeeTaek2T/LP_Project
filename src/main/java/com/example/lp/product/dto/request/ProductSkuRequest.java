package com.example.lp.product.dto.request;

public record ProductSkuRequest(String skuCode,
                                String color,
                                String size,
                                Long stock,
                                Long price,
                                boolean inActive) {}
