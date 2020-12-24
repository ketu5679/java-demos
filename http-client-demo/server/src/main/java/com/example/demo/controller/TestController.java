package com.example.demo.controller;

import com.example.demo.ResponseResult;
import com.example.demo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zjh
 * @Date: 2020/12/23 17:19
 */
@RestController
@Validated
@Slf4j
public class TestController {

    /**
     * 无法进行参数验证，requestParam里面为user的各个属性
     * @param user
     * @return
     */
    @GetMapping("/get1")
    public ResponseResult tGet(User user) {
        return new ResponseResult().setCode(1).setInfo(user);
    }

    @GetMapping("/get2")
    public ResponseResult tGet2(HttpServletRequest request, User user) {
        log.info(request.getQueryString());
        log.info(request.getParameter("ddd"));
        return new ResponseResult().setCode(1).setInfo(user);
    }

    @GetMapping("/get3")
    public ResponseResult tGet3(HttpServletRequest request,
                                @RequestParam("id") Integer id,
                                @RequestParam("name") String name,
                                @RequestParam("subIds") List<Integer> ids) {
        System.out.println("====>get3");
        System.out.println(id);
        System.out.println(name);
        System.out.println(ids);
        System.out.println("===queryString");
        log.info(request.getQueryString());
        System.out.println("==== getId");
        log.info(request.getParameter("id"));
        return new ResponseResult().setCode(1).setInfo(name);
    }

    @PostMapping("/post")
    public ResponseResult tPost() {
        HashMap<String, Integer> res = new HashMap<>();
        res.put("a", 1);
        res.put("b", 2);
        return new ResponseResult().setCode(1).setInfo(res);
    }
}
