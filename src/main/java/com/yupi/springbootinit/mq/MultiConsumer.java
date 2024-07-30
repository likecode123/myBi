package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class MultiConsumer {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    final Connection connection = factory.newConnection();
    for (int i = 0; i < 2; i++){

        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//maximum number of messages that the server will deliver /channel
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            try {
                System.out.println(" [x] Received '" + message + "'");
                Thread.sleep(15000); // 模拟耗时任务
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x] Done");
                //手动发送应答，告诉中间件Rabbitmq消息已经被处理
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

  }
}