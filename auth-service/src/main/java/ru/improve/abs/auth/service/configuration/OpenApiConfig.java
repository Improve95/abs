package ru.improve.abs.auth.service.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static ru.improve.abs.auth.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

@Configuration
@SecurityScheme(
        name = SWAGGER_SECURITY_SCHEME_NAME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {}
