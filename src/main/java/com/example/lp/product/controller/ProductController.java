package com.example.lp.product.controller;

import com.example.lp.product.dto.request.ProductRequest;
import com.example.lp.product.dto.response.ProductResponse;
import com.example.lp.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @PostMapping("/product")
    public ResponseEntity<URI> registerProduct(@RequestBody ProductRequest productRequest){
        Long registerdProductId = productService.registerProduct(productRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registerdProductId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<ProductResponse> productResponseList = productService.getAllProduct();
        return ResponseEntity.ok(productResponseList);
    }


}
