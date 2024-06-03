package com.ssafy.trip.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.trip.config.JwtUtil;

@Service
public class UserService {

	@Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60 * 24L;
    public String login(String userName, String password) {

        return JwtUtil.createJwt(userName, secretKey, expiredMs);
    }
}