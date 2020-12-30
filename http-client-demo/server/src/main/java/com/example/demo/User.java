package com.example.demo;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * @Author: zjh
 * @Date: 2020/12/23 17:52
 */
@Data
public class User {
    private Integer id;
    @NotNull
    private String name;

    User() {
//        name = "ff";
    }
}
