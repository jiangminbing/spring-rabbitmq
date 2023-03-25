package com.example.demo.handler;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangmb
 * @version 1.0.0
 * @date 2023-03-23 16:53
 */
@Component
public class CustomerModeHandler {

    @RabbitListener(queues = {"topic-4"})
    public void receiveMessage1(Message message, Channel channel) throws IOException {
        // 处理消息
        try {
            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
            // 消息处理逻辑
            System.out.println(LocalDateTime.now().format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"消费者1 Received message: " + messageBody);
            // 手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 消息处理异常，手动拒绝消息并重新入队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
    @RabbitListener(queues = {"topic-4"})
    public void receiveMessage2(Message message, Channel channel) throws IOException {
        // 处理消息
        try {
            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
            // 消息处理逻辑
            System.out.println(LocalDateTime.now().format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"消费者2 Received message: " + messageBody);
//            TimeUnit.SECONDS.sleep(10);
            // 手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 消息处理异常，手动拒绝消息并重新入队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
