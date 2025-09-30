package com.example.lp.product.service;

import com.example.lp.event.entity.EventItem;
import com.example.lp.event.repository.EventItemRepository;
import com.example.lp.s3.service.S3ImageService;
import com.example.lp.security.handler.ImageHandler;
import com.example.lp.product.dto.request.ProductRequest;
import com.example.lp.product.dto.response.ProductAndSkuResponse;
import com.example.lp.product.dto.response.ProductSkuResponse;
import com.example.lp.product.entity.Product;
import com.example.lp.product.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductSkuService productSkuService;
    private final EventItemRepository eventItemRepository;
    private final S3ImageService s3ImageService;

    public ProductService(ProductRepository productRepository,
                          ProductSkuService productSkuService, EventItemRepository eventItemRepository,
                          S3ImageService s3ImageService) {
        this.productRepository = productRepository;
        this.productSkuService = productSkuService;
        this.eventItemRepository = eventItemRepository;
        this.s3ImageService = s3ImageService;
    }

    public void registerProduct(ProductRequest productRequest, MultipartFile coverImage) {

        String savedCoverImageUrl = s3ImageService.upload(coverImage);
        Product product = new Product(productRequest.name(), productRequest.price(), savedCoverImageUrl, productRequest.category());
        Product savedProduct = productRepository.save(product);
    }

    @Cacheable(
            value = "prod:list",
            key = "'v1:p=' + #page + ':s=' + #size",
            sync = true
    )
    public List<ProductAndSkuResponse> getAllProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        return convertToProductAndSkuResponseList(productPage.getContent());
    }

    private List<ProductAndSkuResponse> convertToProductAndSkuResponseList(List<Product> productList){
        List<ProductAndSkuResponse> productAndSkuResponseList = new ArrayList<>();
        for(Product product : productList){
            List<ProductSkuResponse> productSkuResponseList = productSkuService.convertToProductSkuResponseList(product);
            ProductAndSkuResponse productAndSkuResponse = confirmProductEventItem(product, productSkuResponseList);
            productAndSkuResponseList.add(productAndSkuResponse);
        }
        return productAndSkuResponseList;
    }

    private ProductAndSkuResponse confirmProductEventItem(Product product, List<ProductSkuResponse> productSkuResponseList){
        if (product.getSaled()){
            EventItem eventItem = eventItemRepository.findByProduct(product)
                    .orElseThrow(() -> new RuntimeException());
            return new ProductAndSkuResponse(product.getId(), product.getName(), product.getPrice(), product.getCategory(),
                    product.getCoverImageUrl(), product.getSaled(), eventItem.getSalePrice(), productSkuResponseList);
        }
        return new ProductAndSkuResponse(product.getId(), product.getName(), product.getPrice(), product.getCategory(),
                product.getCoverImageUrl(), product.getSaled(), null, productSkuResponseList);
    }

    public List<ProductAndSkuResponse> getAllProductForSeller() {
        List<Product> productList = productRepository.findAll();
        List<ProductAndSkuResponse> productAndSkuResponseList = convertToProductAndSkuResponseListForSeller(productList);
        return productAndSkuResponseList;
    }

    private List<ProductAndSkuResponse> convertToProductAndSkuResponseListForSeller(List<Product> productList){
        List<ProductAndSkuResponse> productAndSkuResponseList = new ArrayList<>();
        for(Product product : productList){
            List<ProductSkuResponse> productSkuResponseList = productSkuService.convertToProductSkuResponseListForSeller(product);
            ProductAndSkuResponse productAndSkuResponse = confirmProductEventItem(product, productSkuResponseList);
            productAndSkuResponseList.add(productAndSkuResponse);
        }
        return productAndSkuResponseList;
    }

    public ProductAndSkuResponse getProductAndSkuByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException());
        ProductAndSkuResponse productAndSkuResponse = convertToProductAndSkuResponse(product);
        return productAndSkuResponse;
    }

    public ProductAndSkuResponse convertToProductAndSkuResponse(Product product){
        List<ProductSkuResponse> productSkuResponseList = productSkuService.convertToProductSkuResponseListForIndividualProduct(product);
        ProductAndSkuResponse productAndSkuResponse = confirmProductEventItemForSearchByProductId(product, productSkuResponseList);
        return productAndSkuResponse;
    }

    private ProductAndSkuResponse confirmProductEventItemForSearchByProductId(Product product, List<ProductSkuResponse> productSkuResponseList){
        if (product.getSaled()){
            EventItem eventItem = eventItemRepository.findByProduct(product)
                    .orElseThrow(() -> new RuntimeException());
            return new ProductAndSkuResponse(null, product.getName(), product.getPrice(), null,
                    null, product.getSaled(), eventItem.getSalePrice(), productSkuResponseList);
        }
        return new ProductAndSkuResponse(null, product.getName(), product.getPrice(), null,
                product.getCoverImageUrl(), product.getSaled(), null, productSkuResponseList);
    }
}