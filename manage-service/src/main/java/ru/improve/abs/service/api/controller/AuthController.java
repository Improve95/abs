package ru.improve.abs.service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.service.api.controller.spec.AuthControllerSpec;
import ru.improve.abs.service.api.dto.auth.LoginRequest;
import ru.improve.abs.service.api.dto.auth.LoginResponse;
import ru.improve.abs.service.api.dto.auth.ResetPasswordRequest;
import ru.improve.abs.service.api.dto.user.SignInRequest;
import ru.improve.abs.service.api.dto.user.SignInResponse;
import ru.improve.abs.service.core.security.service.AuthService;

import static ru.improve.abs.service.api.ApiPaths.AUTH;
import static ru.improve.abs.service.api.ApiPaths.LOGIN;
import static ru.improve.abs.service.api.ApiPaths.LOGOUT;
import static ru.improve.abs.service.api.ApiPaths.PASSWORD;
import static ru.improve.abs.service.api.ApiPaths.RESET;
import static ru.improve.abs.service.api.ApiPaths.RESET_LINK;
import static ru.improve.abs.service.api.ApiPaths.SIGN_IN;

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

    @PostMapping(LOGOUT)
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @PostMapping(PASSWORD + RESET)
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.sendLinkForResetPassword(resetPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(PASSWORD + RESET_LINK)
    public ResponseEntity<Void> resetPassword(@RequestParam(required = false) String token) {
        authService.resetPassword(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
