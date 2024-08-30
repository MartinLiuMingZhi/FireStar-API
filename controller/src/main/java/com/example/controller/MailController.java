package com.example.controller;

import cn.hutool.log.Log;
import com.example.common.Result;
import com.example.common.UserConstant;
import com.example.model.dto.VerifyCodeRequest;
import com.example.service.MailService;
import com.example.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.regex.Pattern;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private RedisService redisService;

    @GetMapping("/sendCode")
    public Result sendCode(String email){

        if (!Pattern.matches(UserConstant.EMAIL_PATTERN,email)){
            return Result.error("401","邮箱格式错误");
        }

        String code = String.valueOf(new Random().nextInt(899999)+100000);

        // 保存验证码到Redis并设置有效期
        redisService.saveCode(email, code);

        String message = "您的验证码为: "+code+" ,有效期为5分钟";
        try {
            mailService.sendHtmlMail(email,"验证码",message);
            return Result.success("验证码已发送到指定邮箱");
        }catch (Exception e){
            log.info(e.getMessage());
            return Result.error("500","验证码发送失败");
        }
    }

    @PostMapping("/verifyCode")
    public Result verifyCode(@RequestBody VerifyCodeRequest request){

        if (!Pattern.matches(UserConstant.EMAIL_PATTERN,request.getEmail())){
            return Result.error("401","邮箱格式错误");
        }

        String storedCode = redisService.getCode(request.getEmail());

        if (storedCode != null && storedCode.equals(request.getCode())) {
            // 验证码正确，删除验证码
            redisService.deleteCode(request.getEmail());
            return Result.success("验证码验证成功");
        } else {
            return Result.error("验证码错误或已过期");
        }
    }

}
