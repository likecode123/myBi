package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class FanoutConsumer {
  private static final String EXCHANGE_NAME = "fanout-exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    //创建两个通道
    Channel channel1 = connection.createChannel();
    Channel channel2 = connection.createChannel();


    String queueName1 = "first_queue";
    channel1.queueDeclare(queueName1, true, false, false, null);
    channel1.queueBind(queueName1, EXCHANGE_NAME, "");
    // 创建队列2
    String queueName2 = "second_queue";
    channel2.queueDeclare(queueName2, true, false, false, null);
    channel2.queueBind(queueName2, EXCHANGE_NAME, "");

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    // 创建交付回调函数1
    DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
      String message = new String(delivery.getBody(), "UTF-8");
      System.out.println(" [小王] Received '" + message + "'");
    };
    // 创建交付回调函数2
    DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
      String message = new String(delivery.getBody(), "UTF-8");
      System.out.println(" [小李] Received '" + message + "'");
    };

    // 开始消费消息队列1
    channel1.basicConsume(queueName1, true, deliverCallback1, consumerTag -> { });
    // 开始消费消息队列2
    channel2.basicConsume(queueName2, true, deliverCallback2, consumerTag -> { });
  }
}