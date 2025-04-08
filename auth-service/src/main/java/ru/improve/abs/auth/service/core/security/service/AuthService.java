package ru.improve.abs.auth.service.core.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.improve.abs.auth.service.api.dto.auth.LoginRequest;
import ru.improve.abs.auth.service.api.dto.auth.LoginResponse;
import ru.improve.abs.auth.service.api.dto.auth.TokenExchangeResponse;
import ru.improve.abs.auth.service.api.dto.user.SignInRequest;
import ru.improve.abs.auth.service.api.dto.user.SignInResponse;

public interface AuthService {

    boolean setAuthentication(HttpServletRequest request, HttpServletResponse response);

    TokenExchangeResponse exchangeGatewayToken(HttpServletRequest request);

    SignInResponse signIn(SignInRequest signInRequest);

    LoginResponse login(LoginRequest loginRequest);

    void logout();
}
