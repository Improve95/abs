package ru.improve.abs.service.api.controller.spec;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import ru.improve.abs.service.api.dto.auth.LoginRequest;
import ru.improve.abs.service.api.dto.auth.LoginResponse;
import ru.improve.abs.service.api.dto.auth.ResetPasswordGetLinkRequest;
import ru.improve.abs.service.api.dto.auth.ResetPasswordSendPasswordRequest;
import ru.improve.abs.service.api.dto.auth.SignInRequest;
import ru.improve.abs.service.api.dto.auth.SignInResponse;

import static ru.improve.abs.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface AuthControllerSpec {

    @Operation(summary = "зарегистрировать нового пользователя")
    ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest);

    @Operation(summary = "создать новую сессию для пользователя")
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    @Operation(summary = "выйти из сесии")
    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<Void> logout();

    @Operation(summary = "выйти из всех сессий данного пользователя")
    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<Void> logoutAllSessions();

    @Operation(summary = "прислать на почту токен для восстановления пароля")
    ResponseEntity<Void> resetPassword(ResetPasswordGetLinkRequest resetPasswordGetLinkRequest);

    @Operation(summary = "по присланному токену создать новый пароль")
    ResponseEntity<Void> resetPassword(String token, ResetPasswordSendPasswordRequest resetPasswordSendPasswordRequest);
}
