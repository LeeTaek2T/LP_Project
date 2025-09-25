package com.example.lp.product.dto.response;

import java.util.List;

public record ProductSkuResponse(Long productSkuId,
                                 String size,
                                 String color,
                                 Long quantity,
                                 List<String> productSkuImageUrl) {}

