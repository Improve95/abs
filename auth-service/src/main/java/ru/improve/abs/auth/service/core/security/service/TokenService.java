package ru.improve.abs.auth.service.core.security.service;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import ru.improve.abs.auth.service.model.Session;
import ru.improve.abs.auth.service.model.User;

public interface TokenService {

    Jwt generateToken(User user, Session session);

    Jwt generateToken(JwtClaimsSet claims, String encoderType);

    long getSessionId(Jwt jwt);

    Jwt parseJwt(String jwt, String encoderType);
}
