package com.example.app;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class MyFactoryBean implements FactoryBean {
    Class mapperInterface;

    public MyFactoryBean(Class mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object getObject() throws Exception {
        Class[] classes = {mapperInterface};
        Object o = Proxy.newProxyInstance(
                MyFactoryBean.class.getClassLoader(),
                classes,
                new MyInvocationHandler());
        return o;
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }
}
