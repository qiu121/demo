package com.example.demo;

import com.example.demo.protobuf.MessageProto.Message;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class CreateTestFile {
    public static void main(String[] args) throws IOException {
        // 创建消息
        Message message = Message.newBuilder()
                .setContent("Hello from Apifox")
                .setId(123)
                .addTags("test")
                .addTags("apifox")
                .build();

        // 获取二进制数据
        byte[] bytes = message.toByteArray();

        // 保存为二进制文件
        try (FileOutputStream output = new FileOutputStream("src/main/message.bin")) {
            output.write(bytes);
        }

        // 转换为Base64
        String base64 = Base64.getEncoder().encodeToString(bytes);
        System.out.println("Base64格式:");
        System.out.println(base64);

        // 转换为十六进制
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X ", b));
        }
        System.out.println("\n十六进制格式:");
        System.out.println(hexString.toString().trim());
    }
}