package ru.improve.abs.service.core.security.service;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

public interface TokenService {

//    Jwt generateToken(UserDetails userDetails, Session session);

    Jwt generateToken(JwtClaimsSet claims, String encoderType);

    long getSessionId(Jwt jwt);

    Jwt parseJwt(String jwt, String encoderType);
}
