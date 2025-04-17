package ru.improve.abs.info.service.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.improve.abs.info.service.model.credit.Credit;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@Jacksonized
public class PaymentsResponse {

    private long id;

    private BigDecimal amount;

    private BigDecimal commissionAmount;

    private Instant createdAt;

    private Credit credit;
}
