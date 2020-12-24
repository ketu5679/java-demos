package com.example.demo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: zjh
 * @Date: 2020/12/23 17:19
 */
@Data
@Accessors(chain=true)
public class ResponseResult {
    private Integer code;
    private String msg;
    private Object info;
}
