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
//@RabbitListener(queues = "${spring.rabbitmq.test.queue}")
public class DirectStringReceiver {
    @RabbitHandler
    public void process(String testMessage) {
        System.out.println("DirectStringReceiver消费者收到消息  : " + testMessage);
    }
}
