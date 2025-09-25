package com.example.lp.order.service;

import com.example.lp.member.entity.Member;
import com.example.lp.order.dto.request.OrderProductInfo;
import com.example.lp.order.entity.Order;
import com.example.lp.order.entity.OrderDetail;
import com.example.lp.order.repository.OrderDetailRepository;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductSkuRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductSkuRepository productSkuRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository, ProductSkuRepository productSkuRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.productSkuRepository = productSkuRepository;
    }

    public void createOrderDetail(Order order, Member member,
                                  List<OrderProductInfo> orderProductInfoList){
        for(OrderProductInfo orderProductInfo : orderProductInfoList){
            ProductSku productSku = productSkuRepository.findById(orderProductInfo.productSkuId())
                    .orElseThrow(() -> new RuntimeException("<UNK>"));
            OrderDetail orderDetail = new OrderDetail(orderProductInfo.price(), orderProductInfo.name(),
                    orderProductInfo.coverImageUrl(), orderProductInfo.quantity(), orderProductInfo.size(),
                    orderProductInfo.color(), member, productSku, order);
            orderDetailRepository.save(orderDetail);
        }

    }
}
