package ru.improve.abs.info.service.core.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    boolean setAuthentication(HttpServletRequest httpRequest, HttpServletResponse response);
}
