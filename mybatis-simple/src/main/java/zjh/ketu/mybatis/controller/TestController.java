package zjh.ketu.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zjh.ketu.mybatis.entity.Test;
import zjh.ketu.mybatis.service.TestService;

/**
 * @Author: zjh
 * @Date: 2020/11/16 15:45
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/add")
    public Object add() {
        testService.add(Test.builder().id(1).name("tt").build());
        return 1;
    }

    @GetMapping("/list")
    public Object test() {
        return testService.listAll();
    }
}
