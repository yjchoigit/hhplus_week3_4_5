package com.hhplus.hhplus_week3_4_5.ecommerce.base.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtTokenUtil {
    private static final String SECRET_KEY = "your_strong_secret_key_which_is_32_bytes_long";

    public static String generateToken(Long buyerId) {
        return Jwts.builder()
                .claim("buyerId", buyerId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 유효
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

    public static void main(String[] args) {
        try {
            // buyerId 1L로 JWT 토큰 생성
            Long buyerId = 1L;
            String token = generateToken(buyerId);
            System.out.println("Generated JWT Token: " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
