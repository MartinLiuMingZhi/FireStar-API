package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.common.exception.ServiceException;
import com.example.dao.UserMapper;
import com.example.model.dto.LoginRequest;
import com.example.model.dto.LoginResponse;
import com.example.model.dto.RegisterRequest;
import com.example.model.dto.RegisterResponse;
import com.example.model.pojo.User;
import com.example.service.RedisService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private RedisService redisService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        if (StringUtils.isAnyBlank(email,password)){
            return Result.error("账号或密码为空");
        }
        LoginResponse userInfo = userService.login(loginRequest);

        // 保存令牌到 Redis 中
        redisService.saveToken(userInfo.getToken(), email);

        return Result.success(userInfo);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterRequest registerRequest){
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String checkPassword = registerRequest.getCheckPassword();
        String code = registerRequest.getCode();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",registerRequest.getEmail());
        User user = userService.getOne(queryWrapper);
        if (user != null){
            return Result.error("402","注册失败,用户已存在");
        }

        //验证邮箱验证码
        String storedCode = redisService.getCode(email);
        if (!(storedCode != null && storedCode.equals(code))) {
            // 验证码正确，删除验证码
            redisService.deleteCode(email);
            return Result.error("401","验证码错误或已过期");
        }

        if (StringUtils.isAnyBlank(email,password,checkPassword,code)){
            return Result.error("400","请确认输入不为空");
        }
        if (!password.equals(checkPassword)){
            return Result.error("401","两次输入的密码不一致");
        }

        RegisterResponse userInfo = userService.register(registerRequest);
        return Result.success(userInfo);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServiceException("Token is missing or invalid");
        }

        String token = authHeader.substring(7);

        // 从 Redis 中删除 token
        redisService.deleteToken(token);

        return Result.success("退出登录成功");
    }

}
