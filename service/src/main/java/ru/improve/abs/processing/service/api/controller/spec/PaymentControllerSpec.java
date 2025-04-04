package ru.improve.abs.processing.service.api.controller.spec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.improve.abs.processing.service.api.dto.payment.PostPaymentRequest;
import ru.improve.abs.processing.service.api.dto.payment.PostPaymentResponse;

import static ru.improve.abs.processing.service.util.message.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface PaymentControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<PostPaymentResponse> payForCredit(@RequestBody @Valid PostPaymentRequest postPaymentRequest);
}
