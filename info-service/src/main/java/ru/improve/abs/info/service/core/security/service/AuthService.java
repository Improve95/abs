package ru.improve.abs.info.service.core.security.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    boolean setAuthentication(HttpServletRequest httpRequest);
}
