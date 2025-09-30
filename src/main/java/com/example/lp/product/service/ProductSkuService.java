package com.example.lp.product.service;

import com.example.lp.s3.service.S3ImageService;
import com.example.lp.security.handler.ImageHandler;
import com.example.lp.product.dto.request.ProductSkuRequest;
import com.example.lp.product.dto.response.ProductSkuResponse;
import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductRepository;
import com.example.lp.product.repository.ProductSkuRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ProductSkuService {

    private final ProductSkuRepository productSkuRepository;
    private final ProductSkuImageService productSkuImageService;
    private final ProductRepository productRepository;
    private final ImageHandler imageHandler;
    private final S3ImageService s3ImageService;

    public ProductSkuService(ProductSkuRepository productSkuRepository,
                             ProductSkuImageService productSkuImageService, ProductRepository productRepository,
                             ImageHandler imageHandler, S3ImageService s3ImageService) {
        this.productSkuRepository = productSkuRepository;
        this.productSkuImageService = productSkuImageService;
        this.productRepository = productRepository;
        this.imageHandler = imageHandler;
        this.s3ImageService = s3ImageService;
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

    public void registerProductSku(Long productId, ProductSkuRequest productSkuRequest,
                                         List<MultipartFile> productSkuImageList) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(""));

        ProductSku productSku = new ProductSku(productSkuRequest.color(), productSkuRequest.size(),
                productSkuRequest.quantity(), product);
        ProductSku savedProductSku = productSkuRepository.save(productSku);

        //sku이미지 생성
        for (MultipartFile productSkuImage : productSkuImageList) {
            String productSkuImageUrl = s3ImageService.upload(productSkuImage);
            productSkuImageService.registerProductSkuImages(productSkuImageUrl, savedProductSku);
        }
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