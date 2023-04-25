package com.ayush.controller;


import com.ayush.dto.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("producer")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    KafkaTemplate<String, Item> kafkaTemplate;

    @Value("${spring.kafka.items-topic}")
    private String topicName;

    String status = "";

    @PostMapping(value = "/sendItem",consumes = {"application/json"},produces = {"application/json"})
    public String postJsonMessage(@RequestBody Item item){
        logger.info("===============================================START===============================================");
       ListenableFuture<SendResult<String, Item>> future = kafkaTemplate.send(topicName,new Item(1,"Lenovo","Laptop"));

       future.addCallback(new ListenableFutureCallback<SendResult<String, Item>>() {
           @Override
           public void onFailure(Throwable ex) {
               logger.info("Message sending failed");
               status = "Message sending failed";
           }

           @Override
           public void onSuccess(SendResult<String, Item> result) {
               logger.info("Message sent successfully");
               status = "Message sent successfully";
           }
       });

        return "Message published successfully";
    }

//  ProducerRecord<String, String> record = new ProducerRecord<>("topicName", "firstRecord-Item");
//
//     producer.send(record, (recordMetadata, exception) -> {
//        if(exception == null){
//            System.out.println(recordMetadata.topic() + "+" + recordMetadata.partition() + "+" + recordMetadata.offset());
//        }else{
//            System.err.println(exception.getMessage());
//        }
//    });



}
