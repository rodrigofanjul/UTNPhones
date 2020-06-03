package com.utn.phones.security;

import io.jsonwebtoken.*;

import java.util.*;

import static com.utn.phones.security.SecurityConstants.SUPER_SECRET_KEY;
import static com.utn.phones.security.SecurityConstants.TOKEN_EXPIRATION_TIME;

public class SecurityProvider {

    public String getToken(String idCard, String role) {
        return Jwts
                .builder()
                .setId("UTN")
                .setSubject(idCard)
                .claim("authorities", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY.getBytes()).compact();
    }
}
