package ru.improve.abs.service.api.controller.spec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.improve.abs.service.api.dto.credit.CreditRequestResponse;
import ru.improve.abs.service.api.dto.credit.CreditResponse;
import ru.improve.abs.service.api.dto.credit.PostCreditRequest;
import ru.improve.abs.service.api.dto.credit.PostCreditRequestRequest;

import static ru.improve.abs.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface CreditControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<CreditRequestResponse> createCreditRequest(
            @RequestBody @Valid PostCreditRequestRequest postCreditRequestRequest
    );

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<CreditResponse> createCredit(@RequestBody @Valid PostCreditRequest creditRequest);
}
