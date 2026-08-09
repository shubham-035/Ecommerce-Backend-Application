package com.auth_nov_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    final static private String SCREATE_KEY="my-secret-key";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public String generateToken(String username,String role){
        return JWT.create()
                .withSubject(username)
                .withClaim("role",role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SCREATE_KEY));

    }
    public String validateJwtTokenAndRetriveUsername(String token){
        return JWT.require(Algorithm.HMAC256(SCREATE_KEY))
                .build()
                .verify(token)
                .getSubject();
    }
}
