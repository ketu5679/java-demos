package com.example.demo.controller;

import com.example.demo.controller.dto.DateTestDTO;
import com.example.demo.controller.dto.NestedTestDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zjh
 * @Date: 2020/11/13 15:46
 */
@RestController
@Validated
public class DemoController {

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/testNested")
    public NestedTestDTO testNested(@RequestBody @Validated NestedTestDTO dto) {
        System.out.println(dto);
        return dto;
    }
    @GetMapping("/testDate")
    public Object testDate(@Validated DateTestDTO dto) {
        System.out.println(dto);
        return dto;
    }


}
