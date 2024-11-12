package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.StatusConstant;
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
        String token = JwtUtil.createJWT(secretKey, expirationTime, dbUser.getEmail());

        if (dbUser.getStatus() == StatusConstant.OFFLINE_STATUS){
            dbUser.setStatus(StatusConstant.ONLINE_STATUS);
            this.updateById(dbUser);
        }

        LoginResponse userInfo = new LoginResponse();
        userInfo.setUserid(dbUser.getId());
        userInfo.setUsername(dbUser.getUsername());
        userInfo.setAvatar(dbUser.getAvatar());
        userInfo.setSex(dbUser.getSex());
        userInfo.setEmail(dbUser.getEmail());
        userInfo.setToken(token);
        userInfo.setStatus(dbUser.getStatus());

        return userInfo;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setUsername("Android Studio");
        user.setAvatar("https://image.liumingzhi.cn/file/20e84180e9663145e6e49.png");
        user.setStatus(StatusConstant.ONLINE_STATUS);
        boolean saveResult = this.save(user);
        if (!saveResult){
            throw new ServiceException("注册失败，数据库错误");
        }
        //创建jwt令牌
        String token = JwtUtil.createJWT(secretKey, expirationTime, user.getEmail());

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setEmail(registerRequest.getEmail());
        registerResponse.setUsername(user.getUsername());
        registerResponse.setAvatar(user.getAvatar());
        registerResponse.setToken(token);
        registerResponse.setStatus(user.getStatus());

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

    @Override
    public Boolean setOfflineStatus(String token) {
        // 获取用户ID
        String email = JwtUtil.getEmailFromToken(secretKey,token); // 假设你有一个JWT工具类来解析token

        // 设置用户状态为0
        User user = new User();
        user.setStatus(StatusConstant.OFFLINE_STATUS);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email );
        boolean result = this.update(user, queryWrapper);
        return result;
    }

    @Override
    public void updateUserStatus(Long userId, boolean online) {

    }
}