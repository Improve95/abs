package ru.improve.abs.service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.service.api.controller.spec.AuthControllerSpec;
import ru.improve.abs.service.api.dto.auth.login.LoginRequest;
import ru.improve.abs.service.api.dto.auth.login.LoginResponse;
import ru.improve.abs.service.api.dto.auth.refresh.RefreshAccessTokenResponse;
import ru.improve.abs.service.api.dto.auth.resetPassword.ResetPasswordGetLinkRequest;
import ru.improve.abs.service.api.dto.auth.resetPassword.ResetPasswordSendPasswordRequest;
import ru.improve.abs.service.api.dto.auth.signin.SignInRequest;
import ru.improve.abs.service.api.dto.auth.signin.SignInResponse;
import ru.improve.abs.service.core.security.service.AuthService;

import static ru.improve.abs.service.api.ApiPaths.AUTH;
import static ru.improve.abs.service.api.ApiPaths.LOGIN;
import static ru.improve.abs.service.api.ApiPaths.LOGOUT;
import static ru.improve.abs.service.api.ApiPaths.LOGOUT_ALL;
import static ru.improve.abs.service.api.ApiPaths.PASSWORD;
import static ru.improve.abs.service.api.ApiPaths.REFRESH;
import static ru.improve.abs.service.api.ApiPaths.RESET;
import static ru.improve.abs.service.api.ApiPaths.SIGN_IN;
import static ru.improve.abs.service.api.ApiPaths.TOKEN;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTH)
public class AuthController implements AuthControllerSpec {

    private final AuthService authService;

    @PostMapping(SIGN_IN)
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        SignInResponse signInResponse = authService.signIn(signInRequest);
        return ResponseEntity.ok(signInResponse);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(REFRESH + TOKEN)
    public ResponseEntity<RefreshAccessTokenResponse> refreshAccessToken(
            @PathVariable String token
    ) {
        RefreshAccessTokenResponse refreshAccessTokenResponse = authService.refreshAccessToken(token);
        return ResponseEntity.ok(refreshAccessTokenResponse);
    }

    @PostMapping(LOGOUT)
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @PostMapping(LOGOUT_ALL)
    public ResponseEntity<Void> logoutAllSessions() {
        authService.logoutAllSessions();
        return ResponseEntity.ok().build();
    }

    @PostMapping(PASSWORD + RESET)
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordGetLinkRequest resetPasswordGetLinkRequest) {
        authService.sendLinkForResetPassword(resetPasswordGetLinkRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(PASSWORD + RESET + TOKEN)
    public ResponseEntity<Void> resetPassword(
            @PathVariable String token,
            @RequestBody ResetPasswordSendPasswordRequest resetPasswordSendPasswordRequest
    ) {
        authService.resetPassword(token, resetPasswordSendPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
