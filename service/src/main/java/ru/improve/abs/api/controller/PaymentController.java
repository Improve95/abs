package ru.improve.abs.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.api.dto.payment.PostPaymentRequest;
import ru.improve.abs.api.dto.payment.PostPaymentResponse;
import ru.improve.abs.core.service.PaymentService;

import static ru.improve.abs.api.ApiPaths.PAYMENTS;

@RequiredArgsConstructor
@RestController
@RequestMapping(PAYMENTS)
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PostPaymentResponse> payForCredit(@RequestBody @Valid PostPaymentRequest postPaymentRequest) {
        PostPaymentResponse postPaymentResponse = paymentService.payForCredit(postPaymentRequest);
        return ResponseEntity.ok(postPaymentResponse);
    }
}
