package com.ireland.ager.chat.config;

import com.ireland.ager.chat.dto.request.MessageRequest;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Class : ProducerConfig
 * @Description : 프로듀서 설정 클래스
 **/
@EnableKafka
@Configuration
public class ProducerConfig {

    /**
     * @Method : producerFactory
     * @Description :
     * @Parameter : []
     * @Return : ProducerFactory<String, MessageRequest>
     **/
    @Bean
    public ProducerFactory<String, MessageRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }

    /**
     * @Method : producerConfigurations
     * @Description :
     * @Parameter : []
     * @Return :  Map<String, Object>
     **/
    @Bean
    public Map<String, Object> producerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER);
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configurations;
    }

    /**
     * @Method : kafkaTemplate
     * @Description :
     * @Parameter : []
     * @Return : KafkaTemplate<String, MessageRequest>
     **/
    @Bean
    public KafkaTemplate<String, MessageRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}