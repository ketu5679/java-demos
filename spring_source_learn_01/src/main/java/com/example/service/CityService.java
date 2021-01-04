package com.example.service;

import com.example.dao.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("c")
public class CityService {
    @Autowired
    CityDao cityDao;

    public void query() {
        cityDao.query().forEach(System.out::println);
    }
}
