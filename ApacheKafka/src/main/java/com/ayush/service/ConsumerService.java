package com.ayush.service;


import com.ayush.dto.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Value("${spring.kafka.items-topic}")
    private String topicName;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @KafkaListener(topics = "${spring.kafka.items-topic}", groupId = "${spring.kafka.consumer.group-id}",containerFactory = "kafkaListener")
    public void listener(Item item) {
        System.out.println("Received message = {}"+ item.toString());
//        ack.acknowledge();  , Acknowledgment ack
    }

//    ConsumerRecord<String, Item>
//    start from the first record available if auto.offset.reset=earliest
//    start from the end (awaiting new messages) if auto.offset.reset=latest
//@KafkaListener(id = "my-client-application", topics = "${topic.name}")
//public void consumer(ConsumerRecord<String, Message> consumerRecord) {
//    System.out.println("Consumed Record Details: " + consumerRecord);
//    Message message = consumerRecord.value();
//    System.out.println("Consumed Message" + message);
//}

//    for(ConsumerRecord<String, String> record: records){
//        System.out.println(record.key() + record.value());
//        System.out.println(record.topic() + record.partition() + record.offset());
//    }
}
