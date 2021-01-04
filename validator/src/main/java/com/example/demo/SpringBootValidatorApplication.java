package com.example.demo;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.apache.catalina.session.StandardSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

@EnableSwagger2Doc
@SpringBootApplication
public class SpringBootValidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootValidatorApplication.class, args);
        Class<StandardSession> ac = StandardSession.class;
        Class<?> httpRequestClass = HttpServletRequest.class;
    }

}
