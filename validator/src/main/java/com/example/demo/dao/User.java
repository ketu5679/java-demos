package com.example.demo.dao;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class User {
    @NotNull
    private Long id;
    @NotBlank
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date date;
}
