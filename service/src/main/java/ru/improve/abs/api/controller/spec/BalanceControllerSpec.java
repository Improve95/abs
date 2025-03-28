package ru.improve.abs.api.controller.spec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PathVariable;

import static ru.improve.abs.util.message.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface BalanceControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    void calcNextDays(@PathVariable @Positive int number);
}
