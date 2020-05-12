package com.utn.UTNPhones.Security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;

import static com.utn.UTNPhones.Security.SecurityConstants.SUPER_SECRET_KEY;
import static com.utn.UTNPhones.Security.SecurityConstants.TOKEN_EXPIRATION_TIME;

public class SecurityProvider {

    public static String tokenProvider(String idCard, String role) {
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
        String token = Jwts
                .builder()
                .setId("UTN")
                .setSubject(idCard)
                .claim("authorities", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY.getBytes()).compact();
        return "Bearer " + token;
    }

    public static String getUserName(final String token) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(SUPER_SECRET_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }
}
