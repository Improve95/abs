package ru.improve.abs.processing.service.util;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.improve.abs.processing.service.api.dto.credit.PostCreditRequest;
import ru.improve.abs.processing.service.core.repository.BalanceRepository;
import ru.improve.abs.processing.service.core.repository.CreditRepository;
import ru.improve.abs.processing.service.core.repository.PaymentRepository;
import ru.improve.abs.processing.service.core.repository.PenaltyRepository;
import ru.improve.abs.processing.service.core.service.CreditService;
import ru.improve.abs.processing.service.model.Balance;
import ru.improve.abs.processing.service.model.credit.Credit;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static ru.improve.abs.processing.service.api.ApiPaths.CREDITS;
import static ru.improve.abs.processing.service.api.ApiPaths.TAKE;

@RequiredArgsConstructor
@Component
public class FillBaseEntityUtil {

	private String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbDFAZ21haWwuY29tIiwic2Vzc2lvbklkIjoxLCJleHAiOjE3NTMyNTI2NTEsImlhdCI6MTc0MzE5NjI1MX0.uWfvtR3XupaPQuYGH4a8cZG1RWyCOaCqhMrTjMqK4iA";

	private final CreditService creditService;

	private final CreditRepository creditRepository;

	private final BalanceRepository balanceRepository;

	private final PenaltyRepository penaltyRepository;

	private final PaymentRepository paymentRepository;

    public void fillBaseEntity() {

		if (cleanBalance()) {
			return;
		}

		Response response = given().baseUri("http://localhost:8072")
				.contentType(ContentType.JSON)
				.header(new Header("Authorization", "Bearer " + validToken))
				.when()
				.body(PostCreditRequest.builder()
						.initialAmount(BigDecimal.valueOf(100000))
						.percent(16)
						.userId(1)
						.creditPeriod(20)
						.tariffId(1)
						.build()
				)
				.post(CREDITS);
		response = given().baseUri("http://localhost:8072")
				.contentType(ContentType.JSON)
				.header(new Header("Authorization", "Bearer " + validToken))
				.when()
				.post(CREDITS + TAKE + "/1");
    }

	@Transactional
	private boolean cleanBalance() {
		if (balanceRepository.count() > 0) {
			balanceRepository.deleteAll();
			penaltyRepository.deleteAll();
			paymentRepository.deleteAll();
			Credit credit = creditService.findCreditById(1);
			Balance balance = Balance.builder()
					.id(1)
					.credit(credit)
					.remainingDebt(BigDecimal.valueOf(100000))
					.remainingMonthDebt(BigDecimal.valueOf(1405.56))
					.accruedByPercent(BigDecimal.valueOf(43.84))
					.createdAt(LocalDate.now())
					.build();
			balanceRepository.save(balance);
		}
		if (creditRepository.count() > 0) {
			return true;
		}
		return false;
	}
}
