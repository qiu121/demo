package com.example.demo.websocket;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.util.MultiValueMap;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Map;
import com.example.demo.protobuf.MessageProto.Message;


@ServerEndpoint(path = "/ws/{arg}",port = "8080")
public class MyWebSocket {

    @BeforeHandshake
    public void handshake(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String arg, @PathVariable Map pathMap) {
        session.setSubprotocols("stomp");
        if (!"ok".equals(req)) {
            System.out.println("Authentication failed!");
            session.close();
        }
    }

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String arg, @PathVariable Map pathMap) {
        System.out.println("new connection");
        System.out.println(req);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("one connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println(message);
        session.sendText("Hello Netty!");
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        try {
            // 解析接收到的二进制数据
            Message receivedMessage = Message.parseFrom(bytes);


            // 处理消息内容
            System.out.println("Content: " + receivedMessage.getContent());
            System.out.println("ID: " + receivedMessage.getId());
            System.out.println("Tags: " + receivedMessage.getTagsList());

            // 创建响应消息
            Message responseMessage = Message.newBuilder()
                    .setContent("Server response: " + receivedMessage.getContent())
                    .setId(receivedMessage.getId() + 1)
                    .addTags("server_processed")
                    .build();

            // 发送响应
            session.sendBinary(responseMessage.toByteArray());

        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to parse protobuf message: " + e.getMessage());
            session.close();
        }
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }

}