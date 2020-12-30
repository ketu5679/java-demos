package com.example.swagger.controller;

import com.example.swagger.annotation.SwaggerCustomIgnore;
import com.example.swagger.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/user")
public class UserController {
    @SwaggerCustomIgnore
    @GetMapping("/list")
    public Object getUserList(){
        return Collections.singletonList(User.builder().age(18).username("枣面包").build());
    }

    @PostMapping("create")
    public Object creteUser(){
        return User.builder().age(18).username("枣面包").build();
    }
}
