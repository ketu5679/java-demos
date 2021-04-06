package com.example.springbootopenfeignclient;

import ch.qos.logback.core.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.clientconfig.HttpClientFeignConfiguration;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootOpenFeignClientApplicationTests {

    @Autowired
    UserService userService;
    @Test
    void contextLoads() throws InterruptedException {
        System.out.println(userService.test());;
        System.out.println(userService.test404());
        User user = userService.test();
        System.out.println(user.getCreateTime().getHour());
        TimeUnit.SECONDS.sleep(1000);
    }

}
