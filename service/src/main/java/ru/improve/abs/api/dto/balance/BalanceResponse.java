package ru.improve.abs.api.dto.balance;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Jacksonized
public class BalanceResponse {

    private long id;

    private long creditId;

    private BigDecimal remainingDebt;

    private BigDecimal remainingMonthDebt;

    private BigDecimal accruedByPercent;

    private BigDecimal penalties;

    private LocalDate createdAt;
}
