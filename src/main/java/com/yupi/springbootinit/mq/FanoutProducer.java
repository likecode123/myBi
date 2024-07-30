package com.yupi.springbootinit.mq;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class FanoutProducer {

  private static final String EXCHANGE_NAME = "fanout-exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {
      channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

      //创建一个输入扫描器  用于读取控制台的输入
      Scanner scanner = new Scanner(System.in);
      //使用循环 当用户在控制台输入一行文本  就将其作为消息发送
      while (scanner.hasNext()) {
        String message = scanner.nextLine();

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

      }


    }
  }
}