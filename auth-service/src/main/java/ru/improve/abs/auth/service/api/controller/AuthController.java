package ru.improve.abs.auth.service.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.auth.service.api.controller.spec.AuthControllerSpec;
import ru.improve.abs.auth.service.api.dto.auth.LoginRequest;
import ru.improve.abs.auth.service.api.dto.auth.LoginResponse;
import ru.improve.abs.auth.service.api.dto.auth.TokenExchangeResponse;
import ru.improve.abs.auth.service.api.dto.user.SignInRequest;
import ru.improve.abs.auth.service.api.dto.user.SignInResponse;
import ru.improve.abs.auth.service.core.security.service.AuthService;

import static ru.improve.abs.auth.service.api.ApiPaths.AUTH;
import static ru.improve.abs.auth.service.api.ApiPaths.LOGIN;
import static ru.improve.abs.auth.service.api.ApiPaths.LOGOUT;
import static ru.improve.abs.auth.service.api.ApiPaths.SIGN_IN;
import static ru.improve.abs.auth.service.api.ApiPaths.TOKEN_EXCHANGE;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTH)
public class AuthController implements AuthControllerSpec {

    private final AuthService authService;

    @GetMapping(TOKEN_EXCHANGE)
    public ResponseEntity<TokenExchangeResponse> gatewayTokenExchange(HttpServletRequest request) {
        return ResponseEntity.ok(authService.exchangeGatewayToken(request));
    }

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
