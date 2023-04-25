package com.ayush.config;

import java.util.HashMap;
import java.util.Map;

import com.ayush.dto.Item;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServer;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return props;
    }
//https://www.learningjournal.guru/courses/kafka/kafka-foundation-training/offset-management/
    @Bean
    public ConsumerFactory<String, Item> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<>(Item.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Item> kafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, Item> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
