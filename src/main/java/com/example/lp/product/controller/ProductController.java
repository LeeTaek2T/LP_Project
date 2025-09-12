package com.example.lp.product.controller;

import com.example.lp.product.dto.request.ProductForRegisterationRequest;
import com.example.lp.product.dto.response.ProductAndSkuResponse;
import com.example.lp.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @PostMapping("/seller/product")
    public ResponseEntity<Void> registerProductAndSku(@RequestBody ProductForRegisterationRequest productForRegisterationRequest){
        productService.registerProductAndSku(productForRegisterationRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductAndSkuResponse>> getAllProduct(){
        List<ProductAndSkuResponse> productResponseList = productService.getAllProduct();
        return ResponseEntity.ok(productResponseList);
    }

    @GetMapping("/seller/product")
    public ResponseEntity<List<ProductAndSkuResponse>> getAllProductForSeller(){
        List<ProductAndSkuResponse> productResponseList = productService.getAllProductForSeller();
        return ResponseEntity.ok(productResponseList);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductAndSkuResponse> getProductAndSkuByProductId(@PathVariable Long productId){
        ProductAndSkuResponse productAndSkuResponse = productService.getProductAndSkuByProductId(productId);
        return ResponseEntity.ok(productAndSkuResponse);
    }
}
