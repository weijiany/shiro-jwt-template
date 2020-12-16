package com.example.demo.service;

import com.example.demo.controller.dto.LoginResponseDto;
import com.example.demo.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Value("${account.username}")
    private String username;

    @Value("${account.password}")
    private String password;

    @Value("${account.role}")
    private String role;

    public LoginResponseDto login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password))
            return new LoginResponseDto(JwtUtils.generate(username, role));
        throw new RuntimeException("login error");
    }
}
