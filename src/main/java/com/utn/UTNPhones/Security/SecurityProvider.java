package com.utn.UTNPhones.Security;

import com.utn.UTNPhones.Models.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.utn.UTNPhones.Security.SecurityConstants.SUPER_SECRET_KEY;
import static com.utn.UTNPhones.Security.SecurityConstants.TOKEN_EXPIRATION_TIME;

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
        return "Bearer " + token;
    }

    public static boolean isUser(String idCard) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // This gets the authentication
        if(idCard.equals(authentication.getPrincipal())) return true;
        return false;
    }

    public static boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // This gets the authentication
        if(authentication.getAuthorities().contains(authority)) return true;
        return false;
    }
}
