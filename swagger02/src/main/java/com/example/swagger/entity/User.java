package com.example.swagger.entity;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@ApiModel(value = "User", description = "用户信息")
@Data
@Builder
public class User {
    private String username;
    private Integer age;
}
