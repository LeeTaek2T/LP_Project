package com.example.lp.cart.controller;

import com.example.lp.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/productSku/{productSkuId}")
    public ResponseEntity<Long> addProduct(Authentication auth, @PathVariable("productSkuId") Long productSkuId) {
        Long addedProductId = cartService.addProduct(auth, productSkuId);
        return ResponseEntity.ok(addedProductId);
    }

}
