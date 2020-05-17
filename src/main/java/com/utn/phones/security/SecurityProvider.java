package com.utn.phones.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static com.utn.phones.security.SecurityConstants.SUPER_SECRET_KEY;
import static com.utn.phones.security.SecurityConstants.TOKEN_EXPIRATION_TIME;

public class SecurityProvider {

    public static String tokenProvider(String idCard, String role) {
        String token = Jwts
                .builder()
                .setId("UTN")
                .setSubject(idCard)
                .claim("authorities", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY.getBytes()).compact();
        return token;
    }
}
