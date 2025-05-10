package ru.improve.abs.service.api.controller.spec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import ru.improve.abs.service.api.dto.credit.GetLoansAmountOutputResponse;
import ru.improve.abs.service.api.dto.payment.GetTotalPaymentsResponse;

import java.time.LocalDate;

import static ru.improve.abs.service.util.MessageKeys.SWAGGER_SECURITY_SCHEME_NAME;

public interface ReportControllerSpec {

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<GetLoansAmountOutputResponse> getLoansAmountOutputReport(
            LocalDate from,
            LocalDate to
    );

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<GetTotalPaymentsResponse> getTotalPaymentsResponse(
            LocalDate from,
            LocalDate to
    );

    @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_NAME)
    ResponseEntity<Double> getExpiredCreditsPercentRatio(
            LocalDate from,
            LocalDate to
    );
}
