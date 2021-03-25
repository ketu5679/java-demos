package com.example.springbootopenfeignclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootOpenFeignClientApplicationTests {

    @Autowired
    UserService userService;
    @Test
    void contextLoads() {
        System.out.println(userService.test());;
    }

}
