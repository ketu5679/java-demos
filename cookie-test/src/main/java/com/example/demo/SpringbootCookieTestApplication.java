package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@SpringBootApplication
public class SpringbootCookieTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCookieTestApplication.class, args);
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("time", LocalDateTime.now());
        String requestedSessionId = request.getRequestedSessionId();
        Cookie cookie = new Cookie("a", "fffff");
        cookie.setDomain("ketu.zjh");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        return requestedSessionId;
    }

    @RequestMapping("/get")
    public String get(HttpServletRequest request, HttpServletResponse response) {
        String requestedSessionId = request.getRequestedSessionId();
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            System.out.println(cookies[i].getName() + ":" + cookies[i].getValue());
        }

        return requestedSessionId;
    }
}
