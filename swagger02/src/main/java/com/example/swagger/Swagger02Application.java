package com.example.swagger;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication
public class Swagger02Application {

    public static void main(String[] args) {
        SpringApplication.run(Swagger02Application.class, args);
    }

}
