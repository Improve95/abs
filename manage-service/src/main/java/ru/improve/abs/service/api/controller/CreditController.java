package ru.improve.abs.service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.service.api.controller.spec.CreditControllerSpec;
import ru.improve.abs.service.api.dto.credit.CreditRequestResponse;
import ru.improve.abs.service.api.dto.credit.CreditResponse;
import ru.improve.abs.service.api.dto.credit.PostCreditRequest;
import ru.improve.abs.service.api.dto.credit.PostCreditRequestRequest;
import ru.improve.abs.service.core.service.CreditService;

import static ru.improve.abs.service.api.ApiPaths.CREDITS;
import static ru.improve.abs.service.api.ApiPaths.REQUEST;

@RequiredArgsConstructor
@RestController
@RequestMapping(CREDITS)
public class CreditController implements CreditControllerSpec {

    private final CreditService creditService;

    @PostMapping(REQUEST)
    public ResponseEntity<CreditRequestResponse> createCreditRequest(
            @RequestBody @Valid PostCreditRequestRequest postCreditRequestRequest
    ) {
        CreditRequestResponse creditRequestResponse = creditService.createCreditRequest(postCreditRequestRequest);
        return ResponseEntity.ok(creditRequestResponse);
    }

    @PostMapping
    public ResponseEntity<CreditResponse> createCredit(@RequestBody @Valid PostCreditRequest creditRequest) {
        CreditResponse creditResponse = creditService.createCredit(creditRequest);
        return ResponseEntity.ok(creditResponse);
    }
}
