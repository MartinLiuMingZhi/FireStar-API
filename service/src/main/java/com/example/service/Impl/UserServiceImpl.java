package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.exception.ServiceException;
import com.example.common.untils.JwtUtil;
import com.example.dao.UserMapper;
import com.example.model.dto.LoginRequest;
import com.example.model.dto.LoginResponse;
import com.example.model.dto.RegisterRequest;
import com.example.model.dto.RegisterResponse;
import com.example.model.pojo.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",loginRequest.getEmail());
        queryWrapper.eq("password",loginRequest.getPassword());
        User dbUser = this.baseMapper.selectOne(queryWrapper);
        if (dbUser == null){
            throw new ServiceException("账号不存在或密码错误");
        }
//        String token = TokenUtils.genToken(dbUser.getId().toString(), dbUser.getPassword());
//        dbUser.setToken(token);
        //创建jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", dbUser.getEmail());
        String token = JwtUtil.createJWT(secretKey, expirationTime, claims);

        LoginResponse userInfo = new LoginResponse();
        userInfo.setUserid(dbUser.getId());
        userInfo.setUsername(dbUser.getUsername());
        userInfo.setAvatar(dbUser.getAvatar());
        userInfo.setSex(dbUser.getSex());
        userInfo.setEmail(dbUser.getEmail());
        userInfo.setToken(token);

        return userInfo;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setUsername("Android Studio");
        user.setAvatar("https://image.liumingzhi.cn/file/20e84180e9663145e6e49.png");
        boolean saveResult = this.save(user);
        if (!saveResult){
            throw new ServiceException("注册失败，数据库错误");
        }
        //创建jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        String token = JwtUtil.createJWT(secretKey, expirationTime, claims);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setEmail(registerRequest.getEmail());
        registerResponse.setUsername(user.getUsername());
        registerResponse.setAvatar(user.getAvatar());
        registerResponse.setToken(token);

        return registerResponse;
    }

    @Override
    public List<String> getUsernamesByIds(List<Long> userIds) {
        if (userIds.isEmpty()) {
            return Collections.emptyList();
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id", userIds);
        List<User> users = this.list(userQueryWrapper);

        return users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}