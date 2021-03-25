package com.example.springbootopenfeignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringbootOpenFeignClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOpenFeignClientApplication.class, args);
    }
}
