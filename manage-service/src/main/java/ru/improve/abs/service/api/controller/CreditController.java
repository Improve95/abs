package ru.improve.abs.service.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.service.api.controller.spec.CreditControllerSpec;
import ru.improve.abs.service.api.dto.credit.CreditRequestResponse;
import ru.improve.abs.service.api.dto.credit.CreditResponse;
import ru.improve.abs.service.api.dto.credit.GetLoansAmountOutputResponse;
import ru.improve.abs.service.api.dto.credit.PostCreditRequest;
import ru.improve.abs.service.api.dto.credit.PostCreditRequestRequest;
import ru.improve.abs.service.core.service.CreditService;

import java.time.LocalDate;

import static ru.improve.abs.service.api.ApiPaths.CREDITS;
import static ru.improve.abs.service.api.ApiPaths.LOANS_AMOUNT;
import static ru.improve.abs.service.api.ApiPaths.REPORT;
import static ru.improve.abs.service.api.ApiPaths.REQUEST;

@RequiredArgsConstructor
@RestController
@RequestMapping(CREDITS)
public class CreditController implements CreditControllerSpec {

    private final CreditService creditService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(REPORT + LOANS_AMOUNT)
    public ResponseEntity<GetLoansAmountOutputResponse> getLoansAmountOutputReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        GetLoansAmountOutputResponse getLoansAmountOutputResponse = creditService.getLoansAmountOutputReport(from, to);
        return ResponseEntity.ok(getLoansAmountOutputResponse);
    }

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
