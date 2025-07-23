package ru.improve.abs.service.api.controller.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import ru.improve.abs.service.api.dto.user.UserResponse;

import static ru.improve.abs.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface UserControllerSpec {

    @Operation(summary = "получить данные о пользователе с помощью bearer токена (проверить авторизацию)")
    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<UserResponse> getUserByAuth();

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<UserResponse> becomeClient();
}
