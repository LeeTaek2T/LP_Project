package com.example.lp.order.service;

import com.example.lp.order.dto.request.OrderPreRequest;
import com.example.lp.order.dto.response.OrderPreResponse;
import com.example.lp.order.entity.TossPayment;
import com.example.lp.order.repository.TossPaymentRepository;
import com.example.lp.security.config.TossConfig;
import org.springframework.stereotype.Service;

@Service
public class TossPaymentService {
    private final TossPaymentRepository tossPaymentRepository;


    public TossPaymentService(TossPaymentRepository tossPaymentRepository) {
        this.tossPaymentRepository = tossPaymentRepository;
    }

    public OrderPreResponse savePreOrder(OrderPreRequest orderPreRequest) {
        TossPayment tossPayment = new TossPayment(orderPreRequest.tossOrderId(),
                orderPreRequest.totalAmount(), orderPreRequest.tossPaymentKey());
        TossPayment savedTossPayMent = tossPaymentRepository.save(tossPayment);
        OrderPreResponse orderPreResponse = new OrderPreResponse(savedTossPayMent.getId(),
                tossPayment.getTossOrderId(), tossPayment.getTotalAmount(), tossPayment.getTossPaymentKey());
        return orderPreResponse;
    }

//    public String confirm() {
//        String url = tossConfig.getBaseUrl() + "/v1/payments/confirm";
//        return url;
//    }
}
