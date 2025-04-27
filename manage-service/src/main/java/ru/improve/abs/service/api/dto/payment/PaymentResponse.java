package ru.improve.abs.service.api.dto.payment;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@Jacksonized
public class PaymentResponse {

    private long id;

    private BigDecimal amount;

    private BigDecimal commissionAmount;

    private Instant createdAt;

    private Long creditId;
}
