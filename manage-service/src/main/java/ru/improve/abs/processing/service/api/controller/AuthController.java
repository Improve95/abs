package ru.improve.abs.processing.service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.processing.service.api.controller.spec.AuthControllerSpec;
import ru.improve.abs.processing.service.api.dto.auth.LoginRequest;
import ru.improve.abs.processing.service.api.dto.auth.LoginResponse;
import ru.improve.abs.processing.service.api.dto.user.SignInRequest;
import ru.improve.abs.processing.service.api.dto.user.SignInResponse;

import static ru.improve.abs.processing.service.api.ApiPaths.AUTH;
import static ru.improve.abs.processing.service.api.ApiPaths.LOGIN;
import static ru.improve.abs.processing.service.api.ApiPaths.LOGOUT;
import static ru.improve.abs.processing.service.api.ApiPaths.SIGN_IN;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTH)
public class AuthController implements AuthControllerSpec {

    private final ru.improve.abs.core.security.service.AuthService authService;

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
}
