//package com.example.lp.kafka.consumer;
//
//import com.example.lp.kafka.dto.request.PaymentCompleteMessage;
//import com.example.lp.mail.sevice.AwsMailService;
//import com.example.lp.order.entity.Order;
//import com.example.lp.order.entity.OrderDetail;
//import com.example.lp.order.repository.OrderDetailRepository;
//import com.example.lp.order.repository.OrderRepository;
//import com.example.lp.tosspayment.entity.TossPayment;
//import com.example.lp.tosspayment.repository.TossPaymentRepository;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class KafkaConsumer {
//    private final AwsMailService awsMailService;
//    private final OrderRepository orderRepository;
//    private final OrderDetailRepository orderDetailRepository;
//    private final TossPaymentRepository tossPaymentRepository;
//
//    public KafkaConsumer(AwsMailService awsMailService, OrderRepository orderRepository,
//                         OrderDetailRepository orderDetailRepository, TossPaymentRepository tossPaymentRepository) {
//        this.awsMailService = awsMailService;
//        this.orderRepository = orderRepository;
//        this.orderDetailRepository = orderDetailRepository;
//        this.tossPaymentRepository = tossPaymentRepository;
//    }
//
//    @KafkaListener(topics = "payment-complete-topic", groupId = "mail-service-group", containerFactory = "kafkaListenerContainerFactory")
//    public void handle(PaymentCompleteMessage message) throws Exception {
////        // 1) 중복 처리
////        if (!dedupeService.markIfNotProcessed(message.getEventId())) {
////            // 이미 처리된 이벤트이면 무시
////            return;
////        }
//
//        Order order = orderRepository.findById(message.orderId())
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder(order);
//        TossPayment tossPayment = tossPaymentRepository.findByOrder(order)
//                .orElseThrow(() -> new RuntimeException("TossPayment not found"));
//
//        String dearName = order.getDearName();
//        String address = order.getAddress();
//        String tossOrderId = tossPayment.getTossOrderId();
//        String totalPrice = String.format("%,d",tossPayment.getTotalPrice());
//
//        StringBuilder itemsTable = new StringBuilder();
//        itemsTable.append("<table border='1' cellpadding='5' cellspacing='0'>");
//        itemsTable.append("<tr><th>상품명</th><th>색상</th><th>가격</th><th>수량</th></tr>");
//        for (OrderDetail detail : orderDetailList) {
//            itemsTable.append("<tr>")
//                    .append("<td>").append(detail.getName()).append("</td>")
//                    .append("<td>").append(detail.getColor()).append("</td>")
//                    .append("<td>").append(String.format("%,d", detail.getPrice())).append("원</td>")
//                    .append("<td>").append(detail.getQuantity()).append("</td>")
//                    .append("</tr>");
//        }
//        itemsTable.append("</table>");
//
//        // HTML 이메일 본문
//        String body_html = "<h2>주문 확인 메일</h2>"
//                + "<p>주문자: " + dearName + "</p>"
//                + "<p>주소: " + address + "</p>"
//                + "<p>주문 ID: " + tossOrderId + "</p>"
//                + "<p>총 결제 금액: " + totalPrice + "원</p>"
//                + "<h3>주문 상품 내역</h3>"
//                + itemsTable.toString();
//
//        // 2) 메일 전송
//        try {
//            awsMailService.send(body_html);
//            // 성공 처리 로깅 등
//        } catch (Exception e) {
//            // 예외 던지기 -> DefaultErrorHandler가 재시도/최종 DLQ로 처리
//            throw e;
//        }
//    }
//}
