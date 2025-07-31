//package com.example.demo.kafka;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KafkaConsumer {
//
//    @KafkaListener(topics = {"csp-qk-agv-loc","csp-qk-agv-base"})
//    public void listen(
//            @Payload String message,
//            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//            @Header(KafkaHeaders.RECEIVED_KEY) String key,
//            Acknowledgment ack) {
//
//        System.out.println("Received message from topic: " + topic);
//        System.out.println("Key: " + key);
//        System.out.println("Content: " + message);
//
//        ack.acknowledge();
//    }
//}