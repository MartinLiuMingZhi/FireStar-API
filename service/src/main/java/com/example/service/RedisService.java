package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 保存验证码并设置有效期
    public void saveCode(String email, String code) {
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES); // 5分钟有效期
    }

    // 获取验证码
    public String getCode(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    // 删除验证码
    public void deleteCode(String email) {
        redisTemplate.delete(email);
    }
}
