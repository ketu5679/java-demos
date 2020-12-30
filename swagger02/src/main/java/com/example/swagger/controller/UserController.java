package com.example.swagger.controller;

import com.example.swagger.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Api(tags = "user")
@RestController
@RequestMapping("/user")
public class UserController {
    @ApiOperation(value = "list查询")
    @GetMapping("/list")
    public List<User> getUserList(){
        return Collections.singletonList(User.builder().age(18).username("枣面包").build());
    }

    @PostMapping("create")
    public User creteUser(){
        return User.builder().age(18).username("枣面包").build();
    }
}
