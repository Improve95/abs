package ru.improve.abs.core.service;

import ru.improve.abs.api.dto.balance.BalanceResponse;
import ru.improve.abs.api.dto.balance.PostBalanceRequest;
import ru.improve.abs.model.Balance;
import ru.improve.abs.model.credit.Credit;

import java.math.BigDecimal;

public interface BalanceService {

    BalanceResponse createBalance(PostBalanceRequest postBalanceRequest);

    BalanceResponse editBalanceAfterPayment(BigDecimal paymentAmount, Credit credit);

    Balance findLastCreditBalance(Credit credit);

    BigDecimal calculateCreditMonthAmount(BigDecimal amount, int percent, int duration);

    BigDecimal calculateDailyAccruedByPercentAmount(BigDecimal amount, int percent);
}
