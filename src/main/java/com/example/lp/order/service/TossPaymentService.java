package com.example.lp.order.service;

import com.example.lp.tosspayment.dto.request.PaymentPreRequest;
import com.example.lp.tosspayment.dto.response.ConfirmResponse;
import com.example.lp.tosspayment.dto.response.PaymentResponse;
import com.example.lp.tosspayment.dto.response.PaymentPreResponse;
import com.example.lp.tosspayment.dto.response.TossConfirmResponse;
import com.example.lp.order.entity.Order;
import com.example.lp.tosspayment.entity.TossPayment;
import com.example.lp.order.repository.OrderRepository;
import com.example.lp.order.repository.TossPaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TossPaymentService {
    private final TossPaymentRepository tossPaymentRepository;
    private final OrderRepository orderRepository;


    public TossPaymentService(TossPaymentRepository tossPaymentRepository,
                              OrderRepository orderRepository) {
        this.tossPaymentRepository = tossPaymentRepository;
        this.orderRepository = orderRepository;
    }

    public PaymentPreResponse savePaymentPre(PaymentPreRequest paymentPreRequest, Long dbOrderId) {
        Order order = orderRepository.findById(dbOrderId).orElseThrow(() -> new RuntimeException());
        LocalDateTime requestedAt = LocalDateTime.now();
        TossPayment tossPayment = new TossPayment(paymentPreRequest.orderId(),
                paymentPreRequest.totalAmount(), requestedAt, order);
        TossPayment savedTossPayMent = tossPaymentRepository.save(tossPayment);
        PaymentPreResponse paymentPreResponse = new PaymentPreResponse(savedTossPayMent.getId(),
                tossPayment.getTossOrderId(), tossPayment.getTotalAmount(), savedTossPayMent.getOrder().getId());
        return paymentPreResponse;
    }

    public ConfirmResponse confirm(TossConfirmResponse tossConfirmResponse) {
        TossPayment preTossPayment = tossPaymentRepository.findByTossOrderId(tossConfirmResponse.orderId())
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        TossPayment newTossPayment = new TossPayment(preTossPayment.getId(), preTossPayment.getTossOrderId(),
                tossConfirmResponse.paymentKey(), tossConfirmResponse.method(), tossConfirmResponse.status(),
                preTossPayment.getReqeustedAt(), tossConfirmResponse.approvedAt(), tossConfirmResponse.totalAmount(),
                preTossPayment.getOrder());
        TossPayment savedTossPayment = tossPaymentRepository.save(newTossPayment);
        ConfirmResponse confirmResponse = new ConfirmResponse(savedTossPayment.getTossOrderId(),
                savedTossPayment.getTossPaymentKey(), savedTossPayment.getTotalAmount());
        return confirmResponse;
    }

    public PaymentResponse getConfirmInfoById(Long paymentId) {
        TossPayment tossPayment = tossPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        PaymentResponse paymentResponse = new PaymentResponse(tossPayment.getId(), tossPayment.getTossOrderId(),
                tossPayment.getTossPaymentKey(), tossPayment.getTossPaymentMethod(), tossPayment.getTossPaymentStatus(),
                tossPayment.getReqeustedAt(), tossPayment.getApprovedAt(), tossPayment.getTotalAmount(),
                tossPayment.getOrder().getId());
        return paymentResponse;
    }
}
