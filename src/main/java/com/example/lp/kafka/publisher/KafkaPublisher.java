//package com.example.lp.kafka.publisher;
//
//import com.example.lp.kafka.dto.request.PaymentCompleteMessage;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KafkaPublisher {
//    private final KafkaTemplate<String, PaymentCompleteMessage> kafkaTemplate;
//    private static final String PAYMENT_COMPLETE_TOPIC = "payment-complete-topic";
//
//    public KafkaPublisher(KafkaTemplate<String, PaymentCompleteMessage> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void publishPaymentComplete(PaymentCompleteMessage paymentCompleteMessage) {
//        kafkaTemplate.send(PAYMENT_COMPLETE_TOPIC, paymentCompleteMessage);
//    }
//}
