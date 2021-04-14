package com.example.springbootrabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: zjh
 * @Date: 2021/4/14 16:53
 */
@Component
@RabbitListener(queues = "TestDirectQueue")
public class DirectReceiver {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
    }
}
