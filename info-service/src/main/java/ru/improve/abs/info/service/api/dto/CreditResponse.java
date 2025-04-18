package ru.improve.abs.info.service.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.improve.abs.info.service.model.CreditTariff;
import ru.improve.abs.info.service.model.credit.CreditStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

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

//    private List<Payment> payments;
//
//    private List<Balance> balances;
//
//    private List<Penalty> penalties;
}
