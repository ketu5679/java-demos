package com.example.springbootopenfeignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(name = "userFeignService",
        url = "http://localhost:8080",
        decode404 = true
)
public interface UserService {
    @GetMapping("/test")
    User test();

    @GetMapping("/test404")
    User test404();
}
