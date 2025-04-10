package ru.improve.abs.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.processing.service.api.controller.spec.CreditControllerSpec;
import ru.improve.abs.processing.service.api.dto.credit.CreditRequestResponse;
import ru.improve.abs.processing.service.api.dto.credit.CreditResponse;
import ru.improve.abs.processing.service.api.dto.credit.CreditTariffResponse;
import ru.improve.abs.processing.service.api.dto.credit.PostCreditRequest;
import ru.improve.abs.processing.service.api.dto.credit.PostCreditRequestRequest;
import ru.improve.abs.processing.service.core.service.CreditService;

import java.util.List;

import static ru.improve.abs.processing.service.api.ApiPaths.ALL;
import static ru.improve.abs.processing.service.api.ApiPaths.CREDITS;
import static ru.improve.abs.processing.service.api.ApiPaths.ID;
import static ru.improve.abs.processing.service.api.ApiPaths.PAGE_NUMBER;
import static ru.improve.abs.processing.service.api.ApiPaths.PAGE_SIZE;
import static ru.improve.abs.processing.service.api.ApiPaths.PATH_CREDIT_ID;
import static ru.improve.abs.processing.service.api.ApiPaths.REQUEST;
import static ru.improve.abs.processing.service.api.ApiPaths.TAKE;
import static ru.improve.abs.processing.service.api.ApiPaths.TARIFFS;
import static ru.improve.abs.processing.service.api.ApiPaths.USERS;

@RequiredArgsConstructor
@RestController
@RequestMapping(CREDITS)
public class CreditController implements CreditControllerSpec {

    private final CreditService creditService;

    @GetMapping(TARIFFS)
    public ResponseEntity<List<CreditTariffResponse>> getAllCreditTariffs() {
        List<CreditTariffResponse> creditTariffResponses = creditService.getAllCreditTariffs();
        return ResponseEntity.ok(creditTariffResponses);
    }

    @GetMapping(ALL)
    public ResponseEntity<List<CreditResponse>> getAllCredits(
            @RequestParam(name = PAGE_NUMBER) @Valid int pageNumber,
            @RequestParam(name = PAGE_SIZE) @Valid int pageSize) {
        List<CreditResponse> creditResponses = creditService.getAllCredits(pageNumber, pageSize);
        return ResponseEntity.ok(creditResponses);
    }

    @GetMapping(ID)
    public ResponseEntity<CreditResponse> getCreditById(@PathVariable @Valid @Positive long id) {
        CreditResponse creditResponse = creditService.getCreditById(id);
        return ResponseEntity.ok(creditResponse);
    }

    @GetMapping(USERS + ID)
    public ResponseEntity<List<CreditResponse>> getAllCreditsByUserId(
            @PathVariable @Valid @Positive int id,
            @RequestParam(name = PAGE_NUMBER) @Valid int pageNumber,
            @RequestParam(name = PAGE_SIZE) @Valid int pageSize) {
        List<CreditResponse> creditResponses = creditService.getAllCreditsByUserId(id, pageNumber, pageSize);
        return ResponseEntity.ok(creditResponses);
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

    @PostMapping(TAKE + PATH_CREDIT_ID)
    public ResponseEntity<CreditResponse> takeCreatedCredit(@PathVariable @Valid @Positive int creditId) {
        CreditResponse creditResponse = creditService.takeCreatedCredit(creditId);
        return ResponseEntity.ok(creditResponse);
    }
}
