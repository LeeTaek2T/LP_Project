package com.example.lp.product.service;

import com.example.lp.product.dto.request.ProductSkuForRegisterationRequest;
import com.example.lp.product.dto.response.ProductSkuResponse;
import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductRepository;
import com.example.lp.product.repository.ProductSkuRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductSkuService {

    private final ProductSkuRepository productSkuRepository;
    private final ProductSkuImageService productSkuImageService;

    public ProductSkuService(ProductSkuRepository productSkuRepository,
                             ProductSkuImageService productSkuImageService) {
        this.productSkuRepository = productSkuRepository;
        this.productSkuImageService = productSkuImageService;
    }

    public List<ProductSkuResponse> convertToProductSkuResponseList(Product product){
        List<ProductSku> productSkuList = productSkuRepository.findAllByProductId(product.getId());
        List<ProductSkuResponse> productSkuResponseList = new ArrayList<>();
        for(ProductSku productSku : productSkuList){
            ProductSkuResponse productSkuResponse = new ProductSkuResponse(productSku.getId(), productSku.getSize(),
                    productSku.getColor(), null, null);
            productSkuResponseList.add(productSkuResponse);
        }
        return productSkuResponseList;
    }

    public List<ProductSkuResponse> convertToProductSkuResponseListForEventItem(Product product){
        List<ProductSku> productSkuList = productSkuRepository.findAllByProductId(product.getId());
        List<ProductSkuResponse> productSkuResponseList = new ArrayList<>();
        for(ProductSku productSku : productSkuList){
            ProductSkuResponse productSkuResponse = new ProductSkuResponse(null,  productSku.getSize(),
                    productSku.getColor(), null, null);
            productSkuResponseList.add(productSkuResponse);
        }
        return productSkuResponseList;
    }

    public List<ProductSkuResponse> convertToProductSkuResponseListForSeller(Product product) {
        List<ProductSku> productSkuList = productSkuRepository.findAllByProductId(product.getId());
        List<ProductSkuResponse> productSkuResponseList = new ArrayList<>();
        for(ProductSku productSku : productSkuList){
            ProductSkuResponse productSkuResponse = new ProductSkuResponse(productSku.getId(), productSku.getSize(),
                    productSku.getColor(), productSku.getQuantity(), null);
            productSkuResponseList.add(productSkuResponse);
        }
        return productSkuResponseList;
    }


    //토스 결제 완료 시(confirm함수 실행 시) 결제가 완료됐다는 뜻이므로 reduceProductSku를 실행시켜 결제된 아이템들의 수량을 줄인다.
    public void reduceProductSku(Long productSkuId, Long amount){
        ProductSku productSku = productSkuRepository.findById(productSkuId)
                .orElseThrow(() -> new RuntimeException(""));
        productSku.reduceAmount(amount);
    }

    public void plusProductSku(Long productSkuId, Long amount){
        ProductSku productSku = productSkuRepository.findById(productSkuId)
                .orElseThrow(() -> new RuntimeException(""));
        productSku.plusAmount(amount);
    }

    public List<Long> registerProductSkus(List<ProductSkuForRegisterationRequest> requests, Product product) {
        List<Long> productSkuIdList = new ArrayList<>();
        for (ProductSkuForRegisterationRequest request : requests) {
            ProductSku productSku = new ProductSku(request.color(), request.size(),
                    request.quantity(), product);
            ProductSku savedProductSku = productSkuRepository.save(productSku);

            //sku이미지 생성
            productSkuImageService.registerProductSkuImages(request.productSkuImageUrlList(), savedProductSku);
            productSkuIdList.add(savedProductSku.getId());
        }
        return productSkuIdList;
    }

    public List<ProductSkuResponse> convertToProductSkuResponseListForIndividualProduct(Product product) {
        List<ProductSku> productSkuList = productSkuRepository.findAllByProductId(product.getId());
        List<ProductSkuResponse> productSkuResponseList = new ArrayList<>();
        for(ProductSku productSku : productSkuList){
            List<String> productSkuImageUrlList = productSkuImageService.getAllProductSkuImageUrl(productSku);
            ProductSkuResponse productSkuResponse = new ProductSkuResponse(productSku.getId(), productSku.getSize(),
                    productSku.getColor(), productSku.getQuantity(), productSkuImageUrlList);
            productSkuResponseList.add(productSkuResponse);
        }
        return productSkuResponseList;
    }
}