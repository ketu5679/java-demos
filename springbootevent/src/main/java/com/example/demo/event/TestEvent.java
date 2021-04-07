package com.example.demo.event;

import com.sun.xml.internal.stream.Entity;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: zjh
 * @Date: 2020/11/28 2:46
 */
public class TestEvent extends ApplicationEvent {

    private String source;

    public TestEvent(String source) {
        super(source);
        this.source = source;
    }

    @Override
    public String getSource() {
        return source;
    }
}
