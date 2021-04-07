package com.example.demo;

import com.example.demo.dto.ADTO;
import com.example.demo.dto.BDTO;
import com.example.demo.event.DataEvent;
import com.example.demo.event.TestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: zjh
 * @Date: 2020/11/28 2:48
 */
@Slf4j
@Component
public class EventConsumer {
    @Async
    @EventListener(String.class)
    public void t1(String data) throws IOException {
        log.info("data={}", data);
    }

    @Async
    @EventListener(TestEvent.class)
    public void t2(TestEvent data) throws IOException {
        log.info("data={}", data);
        log.info("dataSource={}", data.getSource());
    }

    @EventListener
    public void t3(DataEvent<Integer> data) throws IOException {
        log.info("t3={}", data);
        log.info("t3Source={}", data.getSource());
    }
    @EventListener
    public void t4(DataEvent<Long> data) throws IOException {
        log.info("t4={}", data);
        log.info("t4Source={}", data.getSource());
    }
    @EventListener
    public void t5(DataEvent<String> data) throws IOException {
        log.info("t5={}", data);
        log.info("t5Source={}", data.getSource());
    }
    @EventListener
    public void ta(DataEvent<ADTO> data) throws IOException {
        log.info("ta={}", data);
        log.info("taSource={}", data.getSource());
    }
    @EventListener
    public void tb(DataEvent<BDTO> data) throws IOException {
        log.info("tb={}", data);
        log.info("tbSource={}", data.getSource());
    }

}
