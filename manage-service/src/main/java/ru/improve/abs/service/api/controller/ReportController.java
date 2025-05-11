package ru.improve.abs.service.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.service.api.controller.spec.ReportControllerSpec;
import ru.improve.abs.service.api.dto.credit.CreditsProfitReportResponse;
import ru.improve.abs.service.api.dto.credit.GetLoansAmountOutputResponse;
import ru.improve.abs.service.api.dto.payment.GetTotalPaymentsResponse;
import ru.improve.abs.service.core.service.CreditService;
import ru.improve.abs.service.core.service.PaymentService;

import java.time.LocalDate;
import java.util.List;

import static ru.improve.abs.service.api.ApiPaths.CREDIT_EXPIRED;
import static ru.improve.abs.service.api.ApiPaths.CREDIT_PROFIT;
import static ru.improve.abs.service.api.ApiPaths.LOANS_AMOUNT;
import static ru.improve.abs.service.api.ApiPaths.PAYMENTS_AMOUNT;
import static ru.improve.abs.service.api.ApiPaths.REPORT;

@RequiredArgsConstructor
@RestController
@RequestMapping(REPORT)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ReportController implements ReportControllerSpec {

    private final CreditService creditService;

    private final PaymentService paymentService;

    @GetMapping(LOANS_AMOUNT)
    public ResponseEntity<GetLoansAmountOutputResponse> getLoansAmountOutputReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        GetLoansAmountOutputResponse getLoansAmountOutputResponse = creditService.getLoansAmountOutputReport(from, to);
        return ResponseEntity.ok(getLoansAmountOutputResponse);
    }

    @GetMapping(PAYMENTS_AMOUNT)
    public ResponseEntity<GetTotalPaymentsResponse> getTotalPaymentsResponse(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        GetTotalPaymentsResponse getTotalPaymentsResponse = paymentService.getTotalPaymentsResponse(from, to);
        return ResponseEntity.ok(getTotalPaymentsResponse);
    }

    @GetMapping(CREDIT_EXPIRED)
    public ResponseEntity<Double> getExpiredCreditsPercentRatio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        double expiredCreditsPercentRatio = creditService.getExpiredCreditPercentRatio(from, to);
        return ResponseEntity.ok(expiredCreditsPercentRatio);
    }

    @GetMapping(CREDIT_PROFIT)
    public ResponseEntity<List<CreditsProfitReportResponse>> getCreditsProfitReport() {
        List<CreditsProfitReportResponse> creditsProfitReport = creditService.getCreditsProfitReport();
        return ResponseEntity.ok(creditsProfitReport);
    }
}
