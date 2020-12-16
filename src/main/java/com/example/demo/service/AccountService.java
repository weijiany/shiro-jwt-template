package com.example.demo.service;

import com.example.demo.controller.dto.LoginResponseDto;
import com.example.demo.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AccountService {

    @Value("${account.username}")
    private String username;

    @Value("${account.password}")
    private String password;

    @Value("${account.role}")
    private String role;

    private final RedisTemplate<String, String> redisTemplate;

    private static final String REDIS_KEY_OF_TOKEN = "token_";
    private static final int TOKEN_TIME_OUT = 180;

    public AccountService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public LoginResponseDto login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            String token = JwtUtils.generate(username, role);
            redisTemplate.opsForValue().set(REDIS_KEY_OF_TOKEN + username, token, TOKEN_TIME_OUT, TimeUnit.SECONDS);
            return new LoginResponseDto(token);
        }
        throw new RuntimeException("login error");
    }

    public boolean checkLoginAndExpireToken(String username, String token) {
        String key = REDIS_KEY_OF_TOKEN + username;
        String tokenInRedis = redisTemplate.opsForValue().get(key);
        if (!token.equals(tokenInRedis))
            return false;

        redisTemplate.expire(key, TOKEN_TIME_OUT, TimeUnit.SECONDS);
        return true;
    }
}
