package com.example.demo.websocket;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.util.MultiValueMap;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import com.example.demo.protobuf.MessageProto.Message;


@ServerEndpoint(path = "/ws/{arg}",port = "8080")
public class MyWebSocket {

        // 存储所有活跃的 WebSocket 会话
        private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

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
        System.out.println(req);
        // 将新会话添加到集合中
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("one connection closed");
        // 从集合中移除关闭的会话
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
        sessions.remove(session);
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

            // 转换为Base64
            String base64 = Base64.getEncoder().encodeToString(bytes);
            System.out.println("Base64格式:");
            System.out.println(base64);

            StringBuilder hexString = new StringBuilder();
            for (byte b : bytes) {
                hexString.append(String.format("%02X ", b));
            }
            System.out.println("\n十六进制格式:");
            System.out.println(hexString.toString().trim());


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

    /**
     * 向所有连接的客户端广播二进制数据
     * @param data 要发送的二进制数据
     */
    public static void broadcastBinary(byte[] data) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendBinary(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 发送失败，移除会话
                    sessions.remove(session);
                }
            }
        }
    }

}