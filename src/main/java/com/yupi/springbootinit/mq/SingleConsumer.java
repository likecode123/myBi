package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class SingleConsumer {
//定义正在监听的队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //从工厂获取一个新的连接 从连接获取一个通道
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//在通道上声明正在监听的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //打印出正在接收消息
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //我们将要告诉服务器将队列中的消息传递给我们。
        // 由于它将异步向我们推送消息，因此我们以对象的形式提供了一个回调，
        // 该回调将缓冲消息，直到我们准备好使用它们。
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        //在频道上开始消费队列中的消息  接收 到的消息会传递给这个子类来处理 持续堵塞
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}