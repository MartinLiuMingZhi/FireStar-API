package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.LoginRequest;
import com.example.model.dto.LoginResponse;
import com.example.model.dto.RegisterRequest;
import com.example.model.dto.RegisterResponse;
import com.example.model.pojo.User;

public interface UserService extends IService<User> {
    LoginResponse login(LoginRequest loginRequest);


    RegisterResponse register(RegisterRequest registerRequest);
}

