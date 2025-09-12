package com.example.lp.product.service;

import com.example.lp.event.entity.EventItem;
import com.example.lp.event.repository.EventItemRepository;
import com.example.lp.product.dto.request.ProductForRegisterationRequest;
import com.example.lp.product.dto.response.ProductAndSkuResponse;
import com.example.lp.product.dto.response.ProductSkuResponse;
import com.example.lp.product.entity.Product;
import com.example.lp.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductSkuService productSkuService;
    private final EventItemRepository eventItemRepository;

    public ProductService(ProductRepository productRepository,
                          ProductSkuService productSkuService, EventItemRepository eventItemRepository) {
        this.productRepository = productRepository;
        this.productSkuService = productSkuService;
        this.eventItemRepository = eventItemRepository;
    }

    public void registerProductAndSku(ProductForRegisterationRequest request) {
        Product product = new Product(request.name(), request.price(), request.coverImageUrl(), request.category());
        Product savedProduct = productRepository.save(product);

        List<Long> productSkuIds = productSkuService.registerProductSkus(request.productSkuForRegisterationRequest(), savedProduct);
    }

    public List<ProductAndSkuResponse> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        List<ProductAndSkuResponse> productAndSkuResponseList = convertToProductAndSkuResponseList(productList);
        return productAndSkuResponseList;
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