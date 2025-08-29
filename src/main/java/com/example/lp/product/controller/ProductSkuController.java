package com.example.lp.product.controller;

import com.example.lp.product.dto.request.ProductSkuRequest;
import com.example.lp.product.dto.response.ProductSkuResponse;
import com.example.lp.product.service.ProductSkuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductSkuController {
    private final ProductSkuService productSkuService;
    public ProductSkuController(ProductSkuService productSkuService){
        this.productSkuService=productSkuService;
    }

    @PostMapping("product/{productId}/productSku")
    public ResponseEntity<URI> registerProductSku(@RequestBody ProductSkuRequest productSkuRequest,
                                                  @PathVariable Long productId){
        Long registedProductSkuId = productSkuService.registerProductSku(productSkuRequest, productId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registedProductSkuId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/product/{productId}/productSku")
    public ResponseEntity<List<ProductSkuResponse>> getAllProductSku(@PathVariable("productId") Long productId){
        List<ProductSkuResponse> productSkuResponseList = productSkuService.getAllProductSku(productId);
        return ResponseEntity.ok(productSkuResponseList);
    }
}
