package ru.improve.abs.service.api.controller.spec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import ru.improve.abs.service.api.dto.role.RoleResponse;

import java.util.List;

import static ru.improve.abs.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface RoleControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<List<RoleResponse>> getRoleList();
}
