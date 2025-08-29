package com.example.lp.event.dto.Request;

import java.time.LocalDateTime;

public record EventItemRequest(Long productSkuId,
                               Long salePrice,
                               Long quotaPerUser,
                               Long stock) {}
