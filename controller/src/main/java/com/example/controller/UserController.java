package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.MyPage;
import com.example.common.Result;
import com.example.common.exception.ServiceException;
import com.example.model.dto.*;
import com.example.model.pojo.User;
import com.example.service.RedisService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/update")
    public Result update(){
        
        return Result.success();
    }

    @GetMapping("/getUsers")
    public Result getUsers(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(null);
        List<User> users = userService.getBaseMapper().selectList(queryWrapper);

        List<UserDTO> userDTOS  = new ArrayList<>();
        for (User user: users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserid(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setUsername(user.getUsername());
            userDTO.setSex(user.getSex());
            userDTO.setAvatar(user.getAvatar());
            userDTOS.add(userDTO);
        }
        return Result.success(userDTOS);
    }

    @GetMapping("/page")
    public Result page(@RequestBody MyPage myPage){
        IPage<User> iPage = new Page<>(myPage.getPage(), myPage.getPageSize());
        IPage<User> newPage = userService.page(iPage,null);

        // 将 User 转换为 UserDTO
        List<UserDTO> userDTOList = newPage.getRecords().stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserid(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setAvatar(user.getAvatar());
            userDTO.setSex(user.getSex());
            return userDTO;
        }).collect(Collectors.toList());

        // 创建新的分页对象
        IPage<UserDTO> userDTOPage = new Page<>(newPage.getCurrent(), newPage.getSize(), newPage.getTotal());
        userDTOPage.setRecords(userDTOList);

        return Result.success(userDTOPage);
    }

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Long id){
        User user = userService.getById(id);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserid(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setSex(user.getSex());
        return Result.success(userDTO);
    }

    @GetMapping("/getUserByEmail")
    public Result getUserByEmail(@RequestParam String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        User user = userService.getOne(queryWrapper);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserid(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setSex(user.getSex());
        return Result.success(userDTO);
    }



}
