package com.example.demo;

import com.example.demo.dto.ADTO;
import com.example.demo.dto.BDTO;
import com.example.demo.event.DataEvent;
import com.example.demo.event.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableAsync
public class SpringbootEventApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEventApplication.class, args);
    }

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * simple
     * @param data
     * @return
     */
    @RequestMapping("/t1")
    public Object t1(@RequestParam("data") String data) {
        applicationEventPublisher.publishEvent(data);
        return data;
    }

    @RequestMapping("/t2")
    public Object t2(@RequestParam("data") String data) {
        applicationEventPublisher.publishEvent(new TestEvent(data));
        return data;
    }

    @RequestMapping("/t3")
    public Object t3(@RequestParam("data") Integer data) {
        applicationEventPublisher.publishEvent(new DataEvent<>(data));
        return data;
    }

    @RequestMapping("/t4")
    public Object t4(@RequestParam("data") Long data) {
        applicationEventPublisher.publishEvent(new DataEvent<>(data));
        return data;
    }
    @RequestMapping("/t5")
    public Object t5(@RequestParam("data") String data) {
        applicationEventPublisher.publishEvent(new DataEvent<>(data));
        return data;
    }
    @RequestMapping("/ta")
    public void t5() {
        applicationEventPublisher.publishEvent(new DataEvent<>(new ADTO("AAAA")));
//        applicationEventPublisher.publishEvent(new DataEvent<>(new BDTO("BBBB")));
    }

}
