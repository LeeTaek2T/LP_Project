package com.example.lp.tosspayment.service;

import com.example.lp.order.entity.OrderDetail;
import com.example.lp.order.repository.OrderDetailRepository;
import com.example.lp.product.service.ProductSkuService;
import com.example.lp.tosspayment.dto.request.PaymentCancelRequest;
import com.example.lp.tosspayment.dto.request.PaymentPreRequest;
import com.example.lp.tosspayment.dto.response.*;
import com.example.lp.order.entity.Order;
import com.example.lp.tosspayment.entity.TossPayment;
import com.example.lp.order.repository.OrderRepository;
import com.example.lp.tosspayment.repository.TossPaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TossPaymentService {
    private final TossPaymentRepository tossPaymentRepository;
    private final OrderRepository orderRepository;
    private final ProductSkuService productSkuService;
    private final OrderDetailRepository orderDetailRepository;


    public TossPaymentService(TossPaymentRepository tossPaymentRepository,
                              OrderRepository orderRepository,
                              ProductSkuService productSkuService, OrderDetailRepository orderDetailRepository) {
        this.tossPaymentRepository = tossPaymentRepository;
        this.orderRepository = orderRepository;
        this.productSkuService = productSkuService;
        this.orderDetailRepository = orderDetailRepository;
    }

    public PaymentPreResponse savePaymentPre(PaymentPreRequest paymentPreRequest, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException());
        OffsetDateTime requestedAt = OffsetDateTime.now();
        TossPayment tossPayment = new TossPayment(paymentPreRequest.tossOrderId(),
                paymentPreRequest.totalAmount(), requestedAt, order);
        TossPayment savedTossPayMent = tossPaymentRepository.save(tossPayment);
        PaymentPreResponse paymentPreResponse = new PaymentPreResponse(savedTossPayMent.getId(),
                tossPayment.getTossOrderId(), tossPayment.getTotalPrice(), savedTossPayMent.getOrder().getId());
        return paymentPreResponse;
    }

    @Transactional
    public ConfirmResponse confirm(TossConfirmResponse tossConfirmResponse) {
        TossPayment preTossPayment = tossPaymentRepository.findByTossOrderId(tossConfirmResponse.orderId())
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        TossPayment newTossPayment = new TossPayment(preTossPayment.getId(), preTossPayment.getTossOrderId(),
                tossConfirmResponse.paymentKey(), tossConfirmResponse.method(), tossConfirmResponse.status(),
                preTossPayment.getReqeustedAt(), tossConfirmResponse.approvedAt(), tossConfirmResponse.totalAmount(),
                preTossPayment.getOrder());
        TossPayment savedTossPayment = tossPaymentRepository.save(newTossPayment);
        ConfirmResponse confirmResponse = new ConfirmResponse(savedTossPayment.getTossOrderId(),
                savedTossPayment.getTossPaymentKey(), savedTossPayment.getTotalPrice(),
                savedTossPayment.getTossPaymentStatus(), savedTossPayment.getApprovedAt());

        //결제 상태 변경 : 결제 중 -> 결제 완료
        Order order = preTossPayment.getOrder();
        order.changeState("결제완료");

        //결제가 됐으므로 재고 개수 차감
//        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder(order);
        for (OrderDetail orderDetail : orderDetailList) {
            productSkuService.reduceProductSku(orderDetail.getProductSku().getId(), orderDetail.getQuantity());
        }
        return confirmResponse;
    }

    public PaymentResponse getConfirmInfoById(Long paymentId) {
        TossPayment tossPayment = tossPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        PaymentResponse paymentResponse = new PaymentResponse(tossPayment.getId(), tossPayment.getTossOrderId(),
                tossPayment.getTossPaymentKey(), tossPayment.getTossPaymentMethod(), tossPayment.getTossPaymentStatus(),
                tossPayment.getReqeustedAt(), tossPayment.getApprovedAt(), tossPayment.getTotalPrice(),
                tossPayment.getOrder().getId());
        return paymentResponse;
    }

    @Transactional
    public PaymentCancelResponse cancelPayment(TossPaymentCancelResponse tossPaymentCancelResponse) {
        TossPayment tossPayment = tossPaymentRepository.findByTossOrderId(tossPaymentCancelResponse.orderId())
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        tossPayment.changeStatus(tossPaymentCancelResponse.status());
        PaymentCancelResponse paymentCancelResponse = new PaymentCancelResponse(tossPayment.getTossPaymentMethod(),
                tossPayment.getTossOrderId(), tossPayment.getTossPaymentStatus(),
                tossPayment.getTotalPrice());

        //결제 취소 -> 해당 주문 상태 변경(취소)
        Order order = tossPayment.getOrder();
        order.changeState("CANCELED");

        //결제 취소 -> 해당 상품들 재고 추가
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder(order);
        for (OrderDetail orderDetail : orderDetailList) {
            productSkuService.plusProductSku(orderDetail.getProductSku().getId(), orderDetail.getQuantity());
        }

        return paymentCancelResponse;
    }

    public String getTossPaymentKey(PaymentCancelRequest paymentCancelRequest) {
        Order order = orderRepository.findById(paymentCancelRequest.orderId())
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        TossPayment tossPayment = tossPaymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException());
        return tossPayment.getTossPaymentKey();
    }
}
