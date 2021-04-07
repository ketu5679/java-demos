package com.example.mybatisplustest.controller;


import com.example.mybatisplustest.mapper.DumUserMapper;
import com.zaxxer.hikari.util.DriverDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjh
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/dumUser")
public class DumUserController {
    @Autowired
    DumUserMapper mapper;
    @GetMapping("/test")
    public Object test() {
        return mapper.selectById(1);
    }

    @GetMapping("/t2")
    public Object t2() {
        DriverDataSource ds = new DriverDataSource(
                "jdbc:presto://8889.ip-10-202-17-239.us-west-2.compute.internal:80/hive",
                "com.facebook.presto.jdbc.PrestoDriver",
                new Properties(), "presto", "");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from dw.dual");
        return maps;
    }
}
