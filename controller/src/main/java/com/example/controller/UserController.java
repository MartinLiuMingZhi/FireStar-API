package com.example.controller;

import com.example.common.Result;
import com.example.model.dto.LoginRequest;
import com.example.model.dto.LoginResponse;
import com.example.model.dto.RegisterRequest;
import com.example.model.pojo.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        if (StringUtils.isAnyBlank(email,password)){
            return Result.error("账号或密码为空");
        }
        LoginResponse userInfo = userService.login(loginRequest);
        return Result.success(userInfo);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterRequest registerRequest){

        return Result.success();
    }
}
