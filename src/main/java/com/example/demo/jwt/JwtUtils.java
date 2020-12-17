package com.example.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.UUID;

public final class JwtUtils {
    private JwtUtils() {}

    public static String generate(String username, String role) {
        return JWT.create()
            .withClaim("salt", UUID.randomUUID().toString())
            .withClaim("username", username)
            .withClaim("role", role)
            .sign(Algorithm.HMAC256("secret"));
    }
}
