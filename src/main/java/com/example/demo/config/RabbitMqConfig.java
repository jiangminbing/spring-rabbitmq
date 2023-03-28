package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class RabbitMqConfig {

    @Bean("rabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                return;
            }
        });

        /**
         * 在找不过交换机和routingkey还是会触发
         */
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("返回消息{}",returned);
            log.error("ReturnCallback:     " + "消息：" + returned.getMessage());
            log.error("ReturnCallback:     " + "回应码：" + returned.getReplyCode());
        });

        return rabbitTemplate;
    }


}
