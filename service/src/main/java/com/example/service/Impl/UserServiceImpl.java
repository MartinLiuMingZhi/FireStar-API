package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.exception.ServiceException;
import com.example.dao.UserMapper;
import com.example.model.dto.LoginRequest;
import com.example.model.dto.LoginResponse;
import com.example.model.pojo.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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
        LoginResponse userInfo = new LoginResponse();
        userInfo.setUserid(dbUser.getUserid());
        userInfo.setUsername(dbUser.getUsername());
        userInfo.setAvatar(dbUser.getAvatar());
        userInfo.setSex(dbUser.getSex());
        userInfo.setEmail(dbUser.getEmail());
        return userInfo;
    }
}