package com.ayush.config;

import java.util.HashMap;
import java.util.Map;

import com.ayush.dto.Item;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        //Create safe producer
//        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
//        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
//        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
//        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));

        //High throughput producer (at the expense of a bit of latency and CPU usage)
//        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
//        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20"); //20ms wait time
//        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); //32KB batch size

        return props;
    }

    @Bean
    public ProducerFactory<String, Item> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Item> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
