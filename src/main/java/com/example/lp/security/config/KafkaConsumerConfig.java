package com.example.lp.security.config;
import com.example.lp.kafka.dto.request.PaymentCompleteMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class    KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, PaymentCompleteMessage> consumerFactory(Environment env) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("KAFKA_BOOTSTRAP", "localhost:9092"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("KAFKA_CONS_GROUP", "mail-service-group"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(PaymentCompleteMessage.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCompleteMessage> kafkaListenerContainerFactory(
            ConsumerFactory<String, PaymentCompleteMessage> consumerFactory,
            KafkaTemplate<String, PaymentCompleteMessage> kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, PaymentCompleteMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

//        FixedBackOff backOff = new FixedBackOff(2000L, 3L);
//        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);
//
//        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

}
