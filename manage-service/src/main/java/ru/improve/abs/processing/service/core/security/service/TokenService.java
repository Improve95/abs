package ru.improve.abs.processing.service.core.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.improve.abs.processing.service.model.Session;

public interface TokenService {

    Jwt generateToken(UserDetails userDetails, Session session);

    long getSessionId(Jwt jwt);

    Jwt parseJwt(String jwt);
}
