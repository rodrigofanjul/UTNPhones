package com.utn.UTNPhones.Security;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.utn.UTNPhones.Security.SecurityConstants.HEADER_AUTHORIZATION_KEY;
import static com.utn.UTNPhones.Security.SecurityConstants.TOKEN_BEARER_PREFIX;
import static com.utn.UTNPhones.Security.SecurityConstants.SUPER_SECRET_KEY;

public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (existJWTToken(request, response)) {
                Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER_AUTHORIZATION_KEY).replace(TOKEN_BEARER_PREFIX, "");
        return Jwts.parser().setSigningKey(SUPER_SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(Claims claims)
    {
        List<GrantedAuthority> authorities = getAuthorities(claims);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private List<GrantedAuthority> getAuthorities(Claims claims) {
        Object authorities = claims.get("authorities");
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String)authorities);
        }
        else if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection)authorities));
        }
        else {
            throw new IllegalArgumentException("Authorities must be either a String or a Collection");
        }
    }

    private boolean existJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER_AUTHORIZATION_KEY);
        if (authenticationHeader == null || !authenticationHeader.startsWith(TOKEN_BEARER_PREFIX)) return false;
        return true;
    }
}