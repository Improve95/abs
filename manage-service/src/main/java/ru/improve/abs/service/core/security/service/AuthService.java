package ru.improve.abs.service.core.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.improve.abs.service.api.dto.auth.login.LoginRequest;
import ru.improve.abs.service.api.dto.auth.login.LoginResponse;
import ru.improve.abs.service.api.dto.auth.refresh.RefreshAccessTokenResponse;
import ru.improve.abs.service.api.dto.auth.resetPassword.ResetPasswordGetLinkRequest;
import ru.improve.abs.service.api.dto.auth.resetPassword.ResetPasswordSendPasswordRequest;
import ru.improve.abs.service.api.dto.auth.signin.SignInRequest;
import ru.improve.abs.service.api.dto.auth.signin.SignInResponse;

public interface AuthService {

    boolean setAuthentication(HttpServletRequest request, HttpServletResponse response);

    SignInResponse signIn(SignInRequest signInRequest);

    LoginResponse login(LoginRequest loginRequest);

    RefreshAccessTokenResponse refreshAccessToken(String refreshToken);

    void logout();

    void logoutAllSessions();

    void sendLinkForResetPassword(ResetPasswordGetLinkRequest resetPasswordGetLinkRequest);

    void resetPassword(String token, ResetPasswordSendPasswordRequest resetPasswordSendPasswordRequest);
}
