package com.utn.UTNPhones.Security;

public class SecurityConstants {

    // Spring Security

    public static final String LOGIN_URL = "/users/**";
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    public static final String SUPER_SECRET_KEY = "abc123";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

}
