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

    /*@SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<List<CreditTariffResponse>> getAllCreditTariffs();

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<List<CreditResponse>> getAllCredits(
            @Valid @RequestParam(name = PAGE_NUMBER) int pageNumber,
            @Valid @RequestParam(name = PAGE_SIZE) int pageSize);

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<CreditResponse> getCreditById(@PathVariable @Valid @Positive long id);

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<List<CreditResponse>> getAllCreditsByUserId(
            @PathVariable @Valid @Positive int id,
            @RequestParam(name = PAGE_NUMBER) @Valid int pageNumber,
            @RequestParam(name = PAGE_SIZE) @Valid int pageSize);*/

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<CreditRequestResponse> createCreditRequest(
            @RequestBody @Valid PostCreditRequestRequest postCreditRequestRequest
    );

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<CreditResponse> createCredit(@RequestBody @Valid PostCreditRequest creditRequest);

//    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
//    ResponseEntity<CreditResponse> takeCreatedCredit(@PathVariable @Valid @Positive int creditId);
}
