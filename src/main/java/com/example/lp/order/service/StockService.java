package com.example.lp.order.service;

import com.example.lp.order.dto.request.OrderProductInfo;
import com.example.lp.order.dto.request.OrderRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    private final RedisTemplate<String, String> redisTemplate;

    public StockService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 주문 요청에 포함된 상품들의 재고를 차감합니다.
     * @return 재고 차감 성공 시 true, 실패 시 false
     */
    public boolean decreaseStock(OrderRequest orderRequest) {
        List<OrderProductInfo> orderProductInfoList = orderRequest.orderProductInfoList();

        // 재고를 성공적으로 차감한 상품 목록 (나중에 롤백을 위해)
        List<OrderProductInfo> successfullyDecremented = new ArrayList<>();

        for (OrderProductInfo product : orderProductInfoList) {
            // DECRBY와 유사하게, 주문 수량만큼 재고를 차감합니다.
            String hashKey = "eventItem:detail:" + product.productSkuId();
            Long currentStock = redisTemplate.opsForHash().increment(hashKey, "quantity", -product.quantity());

            // 재고 차감 후 수량이 0보다 작으면 재고 부족
            if (currentStock != null && currentStock < 0) {
                // 이전에 성공했던 모든 재고를 다시 원상복구(롤백)합니다.
                rollbackStock(successfullyDecremented);
                return false; // 재고 부족으로 실패
            }
            successfullyDecremented.add(product);
        }
        return true; // 모든 상품 재고 차감 성공
    }

    /**
     * 재고 차감에 실패했을 때, 이전에 차감했던 재고를 다시 늘려줍니다. (롤백)
     */
    private void rollbackStock(List<OrderProductInfo> productInfos) {
        for (OrderProductInfo product : productInfos) {
            String hashKey = "eventItem:detail:" + product.productSkuId();
            redisTemplate.opsForHash().increment(hashKey, "quantity", product.quantity());
        }
    }
}