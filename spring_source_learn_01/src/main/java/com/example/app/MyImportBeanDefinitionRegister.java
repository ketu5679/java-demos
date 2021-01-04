package com.example.app;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder b = BeanDefinitionBuilder.genericBeanDefinition(MyFactoryBean.class);
        RootBeanDefinition root = (RootBeanDefinition) b.getBeanDefinition();
        root.getConstructorArgumentValues().addGenericArgumentValue("com.example.dao");
        registry.registerBeanDefinition("xxx", root);
    }
}
