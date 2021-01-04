package com.example.demo.controller;

import com.example.demo.custom.HandsomeBoy;
import com.example.demo.dao.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Api(value = "/user", consumes = "Operations about user")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @ApiOperation(
            value = "Find purchase order by ID",
            notes = "For valid response try integer IDs with value <= 5 or > 10. Other values will generated exceptions",
            response = User.class,
            tags = {"Pet Store"})
    @GetMapping("/list1")
    public User listUser(User user) {

        LinkedMultiValueMap<String, String> a = new LinkedMultiValueMap<>();
        a.add("a","b");
        a.add("a","c");
        List<String> a1 = a.get("a");
        return user;

    }

    @GetMapping("/list2")
    public String listUser2(@RequestParam
                            @ApiParam(value = "Created user object", required = true)
                            @Min(value = 5, message = "ff") String name) {
        return name;
    }

    @PostMapping("/list3")
    @ApiResponse(code = 400, message = "Invalid user supplied")
    public User listUser3(@RequestBody @Valid User user, HttpServletRequest request) throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String value = URLDecoder.decode(cookie.getValue(), "UTF-8");

        }

        return user;
    }

    public User validate3(@Valid @HandsomeBoy(name = "test", message = "test") @RequestBody User user) {
        return user;
    }


}
