package com.example.lp.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        Long totalAmount,
        String address,
        String postCode
) {}
