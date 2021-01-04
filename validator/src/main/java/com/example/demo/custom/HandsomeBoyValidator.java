package com.example.demo.custom;

import com.example.demo.dao.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HandsomeBoyValidator implements ConstraintValidator<HandsomeBoy, User> {

    private String name;

    /**
     * 用于初始化注解上的值到这个validator
     * @param constraintAnnotation
     */
    @Override
    public void initialize(HandsomeBoy constraintAnnotation) {
        name =constraintAnnotation.name();
    }

    /**
     * 具体的校验逻辑
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        return name ==null || name.equals(value.getName());
    }
}
