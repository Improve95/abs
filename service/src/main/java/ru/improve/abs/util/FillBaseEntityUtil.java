package ru.improve.abs.util;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.experimental.UtilityClass;
import ru.improve.abs.api.dto.credit.PostCreditRequest;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static ru.improve.abs.api.ApiPaths.CREDITS;
import static ru.improve.abs.api.ApiPaths.TAKE;

@UtilityClass
public class FillBaseEntityUtil {

	private String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbDFAZ21haWwuY29tIiwic2Vzc2lvbklkIjoxLCJleHAiOjE3NTMyNTI2NTEsImlhdCI6MTc0MzE5NjI1MX0.uWfvtR3XupaPQuYGH4a8cZG1RWyCOaCqhMrTjMqK4iA";

    public void fillBaseEntity() {
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
}
