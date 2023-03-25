package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiangmb
 * @version 1.0.0
 * @date 2023-03-15 11:51
 */
@RestController
@RequestMapping("/v1")
@Slf4j
@Api(value = "测试类", description = "测试类")
public class TestController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation(value = "发送广播消息", notes = "发送广播消息")
    @GetMapping(value = "private/test1")
    public String publishMessage1(
            @RequestParam("name")String name
    ) {
        rabbitTemplate.convertAndSend("MyTest-fanout", null,name);
        return "success";
    }
    @ApiOperation(value = "发送直连消息", notes = "发送直连消息")
    @GetMapping(value = "private/test2")
    public String publishMessage2(
            @RequestParam("name")String name
    ) {
        rabbitTemplate.convertAndSend("MyTest-direct", "queue-1",name);
        return "success";
    }
    @ApiOperation(value = "发送广播消息", notes = "发送广播消息")
    @GetMapping(value = "private/test3")
    public String publishMessage3(
            @RequestParam("name")String name
    ) {
        rabbitTemplate.convertAndSend("MyTest-topic", "my.topic.A",name);
        return "success";
    }
    @ApiOperation(value = "连续发送消息", notes = "连续发送消息")
    @GetMapping(value = "private/test4")
    public String publishMessage(
            @RequestParam("name")String name
    ) {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("ex-topic-4", "ex-topic-4-rk",i+1+"");
        }
        return "success";
    }


}
