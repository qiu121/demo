package com.example.demo.kafka;

import com.example.demo.websocket.MyWebSocket;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.example.demo.protobuf.MessageProto.Message;


@Component
public class KafkaConsumer {

    @KafkaListener(topics = { "csp-qk-agv-base"})
    public void listen(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(value = KafkaHeaders.RECEIVED_KEY,required = false) String key,
            Acknowledgment ack) {

        try {
            // 解析 Kafka 消息
            String content = message;
            int id = 1;
            String tag = "kafka";

            // 创建 Protobuf 消息
            Message protoMessage = Message.newBuilder()
                    .setContent(content)
                    .setId(id)
                    .addTags(tag)
                    .build();

            // 转换为二进制数据
            byte[] binaryData = protoMessage.toByteArray();

            // 通过 WebSocket 发送
            MyWebSocket.broadcastBinary(binaryData);

            // 手动确认消息
            ack.acknowledge();

        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常情况
        }
    }


}