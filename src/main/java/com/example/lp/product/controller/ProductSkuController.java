package com.example.lp.product.controller;

import com.example.lp.product.dto.request.ProductForRegisterationRequest;
import com.example.lp.product.dto.request.ProductSkuRequest;
import com.example.lp.product.service.ProductSkuService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductSkuController {
    private final ProductSkuService productSkuService;
    public ProductSkuController(ProductSkuService productSkuService) {
        this.productSkuService = productSkuService;
    }

    @PostMapping("/seller/product/{productId}/productSku")
    public ResponseEntity<Void> registerProductSku(@PathVariable Long productId,
                                                   @RequestPart ProductSkuRequest productSkuRequest,
                                                   @RequestPart List<MultipartFile> productSkuImageList) {
        productSkuService.registerProductSku(productId, productSkuRequest, productSkuImageList);
        return ResponseEntity.ok().build();
    }
}