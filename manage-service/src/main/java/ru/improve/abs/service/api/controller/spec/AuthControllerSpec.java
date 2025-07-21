package ru.improve.abs.service.api.controller.spec;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import ru.improve.abs.service.api.dto.auth.LoginRequest;
import ru.improve.abs.service.api.dto.auth.LoginResponse;
import ru.improve.abs.service.api.dto.user.SignInRequest;
import ru.improve.abs.service.api.dto.user.SignInResponse;

import static ru.improve.abs.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface AuthControllerSpec {

    ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest);

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<Void> logout();

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<Void> logoutAllSessions();
}
