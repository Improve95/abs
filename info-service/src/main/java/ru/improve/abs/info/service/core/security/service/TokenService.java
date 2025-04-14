package ru.improve.abs.info.service.core.security.service;

import org.springframework.security.oauth2.jwt.Jwt;

public interface TokenService {

    Jwt parseJwt(String jwtToken);
}
