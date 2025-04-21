package ru.improve.abs.info.service.api.dto.credit;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.improve.abs.info.service.model.Balance;
import ru.improve.abs.info.service.model.CreditTariff;
import ru.improve.abs.info.service.model.Payment;
import ru.improve.abs.info.service.model.credit.CreditStatus;
import ru.improve.abs.info.service.model.penalty.Penalty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Jacksonized
public class CreditResponse {

    private long id;

    private BigDecimal initialAmount;

    private LocalDate takingDate;

    private int percent;

    private int creditPeriod;

    private BigDecimal monthAmount;

    private CreditStatus creditStatus;

    private int userId;

    private CreditTariff creditTariff;

    private List<Payment> payments;

    private List<Balance> balances;

    private List<Penalty> penalties;
}
