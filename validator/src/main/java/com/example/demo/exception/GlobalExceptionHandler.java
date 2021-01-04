package com.example.demo.exception;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public String handle(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public String handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> objectErrors = bindingResult.getAllErrors();
        if(!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
        }
        return e.getMessage();
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public String handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cons = e.getConstraintViolations();
        if(!CollectionUtils.isEmpty(cons)){
            StringBuilder msgBuilder = new StringBuilder();
            for(ConstraintViolation constraintViolation : cons){
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if(errorMessage.length()>1){
                errorMessage = errorMessage.substring(0,errorMessage.length()-1);
            }
        }


        return "errorMessage";
    }

    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(ValidationException e) {
        return e.getMessage();
    }
}
