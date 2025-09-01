package com.example.lp.cart.controller;

import com.example.lp.cart.dto.reqeust.CartRequest;
import com.example.lp.cart.dto.response.CartResponse;
import com.example.lp.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/product/{productId}/productSku/{productSkuId}")
    public ResponseEntity<Long> addProduct(Authentication auth, @PathVariable("productId") Long productId,
                                           @PathVariable("productSkuId") Long productSkuId,
                                           @RequestBody CartRequest cartRequest) {
        Long addedProductId = cartService.addProduct(auth, productId, productSkuId, cartRequest);
        return ResponseEntity.ok(addedProductId);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartResponse>> getCart(Authentication auth) {
        List<CartResponse> cartResponseList = cartService.getCart(auth);
        return ResponseEntity.ok(cartResponseList);
    }

}
