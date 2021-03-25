package com.example.springbootopenfeignserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class SpringbootOpenFeignServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOpenFeignServerApplication.class, args);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class User {
        private String name;
        private Integer age;
        private String addr;
        private LocalDateTime createTime;
    }

    @GetMapping("/test")
    public User test() {
        return User.builder().name("ketu").age(27).addr("nj").createTime(LocalDateTime.now()).build();
    }

}
