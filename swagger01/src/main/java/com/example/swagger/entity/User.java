package com.example.swagger.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String username;
    private Integer age;
}
