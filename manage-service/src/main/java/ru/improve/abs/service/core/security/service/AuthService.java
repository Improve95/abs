package ru.improve.abs.service.core.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.improve.abs.service.api.dto.auth.LoginRequest;
import ru.improve.abs.service.api.dto.auth.LoginResponse;
import ru.improve.abs.service.api.dto.auth.ResetPasswordGetLinkRequest;
import ru.improve.abs.service.api.dto.auth.ResetPasswordSendPasswordRequest;
import ru.improve.abs.service.api.dto.user.SignInRequest;
import ru.improve.abs.service.api.dto.user.SignInResponse;

public interface AuthService {

    boolean setAuthentication(HttpServletRequest request, HttpServletResponse response);

    SignInResponse signIn(SignInRequest signInRequest);

    LoginResponse login(LoginRequest loginRequest);

    void logout();

    void logoutAllSessions();

    void sendLinkForResetPassword(ResetPasswordGetLinkRequest resetPasswordGetLinkRequest);

    void resetPassword(String token, ResetPasswordSendPasswordRequest resetPasswordSendPasswordRequest);
}
