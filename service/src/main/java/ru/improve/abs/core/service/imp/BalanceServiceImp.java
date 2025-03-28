package ru.improve.abs.core.service.imp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.improve.abs.api.dto.balance.BalanceResponse;
import ru.improve.abs.api.dto.balance.PostBalanceRequest;
import ru.improve.abs.api.exception.ServiceException;
import ru.improve.abs.core.mapper.BalanceMapper;
import ru.improve.abs.core.repository.BalanceRepository;
import ru.improve.abs.core.repository.CreditRepository;
import ru.improve.abs.core.repository.PaymentRepository;
import ru.improve.abs.core.service.BalanceService;
import ru.improve.abs.model.Balance;
import ru.improve.abs.model.Payment;
import ru.improve.abs.model.credit.Credit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;

import static ru.improve.abs.api.exception.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class BalanceServiceImp implements BalanceService {

    private final CreditRepository creditRepository;

    private final BalanceRepository balanceRepository;

    private final PaymentRepository paymentRepository;

    private final BalanceMapper balanceMapper;

    @Transactional
    @Override
    public BalanceResponse createBalance(PostBalanceRequest postBalanceRequest) {
        Balance balance = balanceMapper.toBalance(postBalanceRequest);
        balance.setCreatedAt(LocalDate.now());
        balance = balanceRepository.save(balance);
        return balanceMapper.toBalanceResponse(balance);
    }

    @Transactional
    @Override
    public BalanceResponse editBalanceAfterPayment(Payment payment) {
        Balance lastBalance = findLastCreditBalance(payment.getCredit());
        BigDecimal remainingDebtAmount = lastBalance.getRemainingMonthDebt();
        BigDecimal remainingMonthDebtAmount = lastBalance.getRemainingMonthDebt();
        BigDecimal paymentAmount = payment.getAmount();
        BigDecimal diffPaymentAndMonthBalance = remainingMonthDebtAmount.subtract(paymentAmount);
        if (remainingMonthDebtAmount.subtract(paymentAmount).compareTo(BigDecimal.valueOf(0)) <= 0) {
            lastBalance.setRemainingDebt(remainingDebtAmount.subtract(diffPaymentAndMonthBalance));
        }
        lastBalance.setRemainingMonthDebt(diffPaymentAndMonthBalance);
        return balanceMapper.toBalanceResponse(lastBalance);
    }

    @Transactional
    @Override
    public void calcCreditBalances(int dayNumber) {
        for (int i = 0; i < dayNumber; i++) {
            Pageable page = PageRequest.of(0, 100);
            for (Credit credit : creditRepository.findAll(page)) {
                calcCredit(credit);
            }
        }
    }

    public void calcCredit(Credit credit) {
        LocalDate todayDate = LocalDate.now();

        final int daysForPayment = 30;
        LocalDate creditTakingDate = credit.getTakingDate();
        int takingDay = creditTakingDate.getDayOfMonth();
        int monthCount = Period.between(creditTakingDate, todayDate).getMonths();
        LocalDate prevMonth = creditTakingDate.plusDays((long) daysForPayment * monthCount);
        if (takingDay == prevMonth.getDayOfMonth()) {
            BigDecimal monthPaymentsSum = paymentRepository.sumAllByCreatedAtBetween(
                    prevMonth.atStartOfDay(ZoneOffset.UTC).toInstant(),
                    todayDate.atStartOfDay(ZoneOffset.UTC).toInstant()
            );
        }

        Balance lastBalance = findLastCreditBalance(credit);
        Balance newBalance = Balance.builder()
                .credit(credit)
                .build();
        if (lastBalance != null) {
            LocalDate lastBalanceDate = lastBalance.getCreatedAt();
            BigDecimal dailyPercentAmount = calculateDailyAccruedByPercentAmount(
                    lastBalance.getRemainingDebt(), credit.getPercent()
            );
            newBalance = newBalance.toBuilder()
                    .remainingDebt(lastBalance.getRemainingDebt())
                    .accruedByPercent(dailyPercentAmount)
                    .penalties(newBalance.getPenalties())
                    .createdAt(lastBalanceDate.plusDays(1))
                    .build();
        } else {
            BigDecimal dailyPercentAmount = calculateDailyAccruedByPercentAmount(
                    credit.getInitialAmount(), credit.getPercent()
            );
            newBalance = newBalance.toBuilder()
                    .remainingDebt(credit.getInitialAmount())
                    .accruedByPercent(dailyPercentAmount)
                    .penalties(BigDecimal.valueOf(0))
                    .createdAt(todayDate)
                    .build();
        }
        balanceRepository.save(newBalance);
    }

    public Balance findLastCreditBalance(Credit credit) {
        Pageable page = PageRequest.of(0, 1)
                .withSort(Sort.by("createdAt").descending());
        Page<Balance> lastBalancePage = balanceRepository.findByCredit(credit, page);
        return lastBalancePage.get()
                .findFirst()
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "balance", "credit"));
    }

    @Override
    public BigDecimal calculateCreditMonthAmount(BigDecimal amount, int percent, int duration) {
        double doublePercent = percent / 100d;
        double monthPart = Math.pow((1 + doublePercent), duration);
        return amount.multiply(
                BigDecimal.valueOf((doublePercent / 12) * monthPart / (monthPart - 1))
        );
    }

    @Override
    public BigDecimal calculateDailyAccruedByPercentAmount(BigDecimal amount, int percent) {
        double doublePercent = percent / 100d;
        return amount.multiply(
                BigDecimal.valueOf(doublePercent / (365 + (LocalDate.now().getYear() % 4 == 0 ? 1 : 0)))
        );
    }
}

