package ru.improve.abs.processing.service.api.controller.spec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import ru.improve.abs.processing.service.api.dto.user.UserResponse;

import static ru.improve.abs.processing.service.util.message.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface UserControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<UserResponse> getUserByAuth();

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<UserResponse> becomeClient();
}
