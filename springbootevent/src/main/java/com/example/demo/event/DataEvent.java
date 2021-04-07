package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: zjh
 * @Date: 2020/11/28 3:02
 */
public class DataEvent<T> extends ApplicationEvent {

    T source;

    public DataEvent(T source) {
        super(source);
        this.source = source;
    }

    @Override
    public T getSource() {
        return source;
    }
}
