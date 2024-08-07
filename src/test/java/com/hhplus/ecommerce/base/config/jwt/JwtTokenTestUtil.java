package com.hhplus.ecommerce.base.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenTestUtil {
    private static final String SECRET_KEY = "your_strong_secret_key_which_is_32_bytes_long";

    public String testGenerateToken(Long buyerId) {
        String token = Jwts.builder()
                .claim("buyerId", buyerId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 유효
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
        System.out.println("test-token : "+ token);
        return token;
    }
}
