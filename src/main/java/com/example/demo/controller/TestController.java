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
    @GetMapping(value = "private/test")
    @Transactional(transactionManager = "",rollbackFor = Exception.class)
    public String run(
            @RequestParam("name")String name
    ) {
        rabbitTemplate.convertAndSend("MyTest-fanout", null,name);
        int b = 100/0;
        return "success";
    }
}
