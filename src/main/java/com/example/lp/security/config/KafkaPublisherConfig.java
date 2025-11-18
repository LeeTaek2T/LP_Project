//package com.example.lp.security.config;
//
//import com.example.lp.kafka.dto.request.PaymentCompleteMessage;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.springframework.core.env.Environment;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaPublisherConfig {
//    @Bean
//    public ProducerFactory<String, PaymentCompleteMessage> producerFactory(
//            ObjectMapper objectMapper, Environment env) {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("KAFKA_BOOTSTRAP", "localhost:9092"));
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(props);
//    }
//
//    @Bean
//    public KafkaTemplate<String, PaymentCompleteMessage> kafkaTemplate(ProducerFactory<String, PaymentCompleteMessage> pf) {
//        return new KafkaTemplate<>(pf);
//    }
//}
