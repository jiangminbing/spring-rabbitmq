package com.example.demo.handler;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author jiangmb
 * @version 1.0.0
 * @date 2023-03-19 17:37
 */
@Component
public class SampleHandler {

    @RabbitListener(queues = {"q1-fanout", "q2-fanout"})
    public void receiveMessage1(Message message, Channel channel) throws IOException {
        // 处理消息
        try {
            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
            // 消息处理逻辑
            System.out.println("Received message: " + messageBody);
            // 手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 消息处理异常，手动拒绝消息并重新入队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "topic-2", durable = "true"),
            exchange = @Exchange(value = "MyTest-topic",type = "topic"),
            key = "my.topic.B"))
    public void receiveMessage2(Message message, Channel channel) throws IOException {
        // 处理消息
        try {
            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
            // 消息处理逻辑
            System.out.println("Received message: " + messageBody);
            // 手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 消息处理异常，手动拒绝消息并重新入队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    @RabbitListener(queues = {"topic-3"})
    public void receiveMessage3(Message message, Channel channel) throws IOException {
        // 处理消息
        try {
            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
            // 消息处理逻辑
            System.out.println("Received message: " + messageBody);
            // 手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 消息处理异常，手动拒绝消息并重新入队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

}
