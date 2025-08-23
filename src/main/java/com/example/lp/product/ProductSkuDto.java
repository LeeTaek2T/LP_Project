package com.example.lp.product;

public record ProductSkuDto(String skuCode,
                            String color,
                            String size,
                            int stock,
                            int price) {}
