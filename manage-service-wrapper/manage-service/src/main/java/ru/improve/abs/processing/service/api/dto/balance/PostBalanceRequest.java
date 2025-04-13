package ru.improve.abs.processing.service.api.dto.balance;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import ru.improve.abs.processing.service.model.credit.Credit;

import java.math.BigDecimal;

@Data
@SuperBuilder(toBuilder = true)
public class PostBalanceRequest {

    private Credit credit;

    private BigDecimal remainingDebt;

    private BigDecimal remainingMonthDebt;

    private BigDecimal accruedByPercent;
}
