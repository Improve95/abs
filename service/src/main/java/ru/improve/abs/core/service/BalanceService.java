package ru.improve.abs.core.service;

import ru.improve.abs.api.dto.balance.BalanceResponse;
import ru.improve.abs.api.dto.balance.PostBalanceRequest;
import ru.improve.abs.model.Payment;

import java.math.BigDecimal;

public interface BalanceService {

    BalanceResponse createBalance(PostBalanceRequest postBalanceRequest);

    BalanceResponse editBalanceAfterPayment(Payment payment);

    void calcCreditBalances(int dayNumber);

    BigDecimal calculateCreditMonthAmount(BigDecimal amount, int percent, int duration);

    BigDecimal calculateDailyAccruedByPercentAmount(BigDecimal amount, int percent);
}
