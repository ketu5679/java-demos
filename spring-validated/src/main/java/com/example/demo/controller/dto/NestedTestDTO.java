package com.example.demo.controller.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: zjh
 * @Date: 2020/11/13 16:35
 */
@Data
public class NestedTestDTO {

    @NotNull
    private Integer id;

    @Valid
    private List<@NotBlank String> users;

}
