package com.example.springbootopenfeignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(name = "userFeignService", url = "http://localhost:8080")
public interface UserService {
    @GetMapping("/test")
    User test();
}
