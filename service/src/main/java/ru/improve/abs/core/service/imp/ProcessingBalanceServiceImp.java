package ru.improve.abs.core.service.imp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.improve.abs.configuration.PaymentPeriodConfig;
import ru.improve.abs.core.repository.BalanceRepository;
import ru.improve.abs.core.repository.CreditRepository;
import ru.improve.abs.core.repository.PenaltyRepository;
import ru.improve.abs.core.service.BalanceService;
import ru.improve.abs.core.service.ProcessingBalanceService;
import ru.improve.abs.model.Balance;
import ru.improve.abs.model.credit.Credit;
import ru.improve.abs.model.credit.CreditStatus;
import ru.improve.abs.model.penalty.Penalty;
import ru.improve.abs.model.penalty.PenaltyType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
@Service
public class ProcessingBalanceServiceImp implements ProcessingBalanceService {

    private final BalanceService balanceService;

    private final CreditRepository creditRepository;

    private final BalanceRepository balanceRepository;

    private final PenaltyRepository penaltyRepository;

    private final PaymentPeriodConfig paymentPeriodConfig;

    @Transactional
    @Override
    public void calcCreditBalances(int dayNumber) {
        for (int i = 0; i < dayNumber; i++) {
            Pageable page = PageRequest.of(0, 100);
            for (Credit credit : creditRepository.findAllByCreditStatus(CreditStatus.OPEN, page)) {
                calcCredit(credit);
            }
        }
    }

    public void calcCredit(Credit credit) {
        boolean isNewPaymentPeriod = false;
        Balance lastBalance = balanceService.findLastCreditBalance(credit);
        LocalDate todayDate = lastBalance.getCreatedAt().plusDays(1);
        LocalDate creditTakingDate = credit.getTakingDate();
        Period period = Period.between(creditTakingDate, todayDate);
        int monthCount = period.getMonths();
        int checkDayInCurrentMonth = creditTakingDate.plusDays(
                (monthCount + 1) * paymentPeriodConfig.getPaymentDuration().toDays()
        ).getDayOfMonth();
        if (todayDate.getDayOfMonth() == checkDayInCurrentMonth) {
            isNewPaymentPeriod = true;
            BigDecimal remainingMonthDebt = lastBalance.getRemainingMonthDebt();
            if (remainingMonthDebt.compareTo(BigDecimal.valueOf(0)) <= 0) {
                BigDecimal totalAccruedByPercent = balanceRepository.sumAccruedByPercentByCreditAndCreatedAtBetween(
                        credit,
                        todayDate.minusDays(paymentPeriodConfig.getPaymentDuration().toDays()),
                        todayDate
                );
                lastBalance.setRemainingDebt(
                        lastBalance.getRemainingDebt().subtract(credit.getMonthAmount().subtract(totalAccruedByPercent))
                );
            } else {
                penaltyRepository.save(Penalty.builder()
                        .credit(credit)
                        .type(PenaltyType.MAIN_DEBT_REMAIN)
                        .amount(remainingMonthDebt)
                        .createdAt(todayDate)
                        .build()
                );
                penaltyRepository.save(Penalty.builder()
                        .credit(credit)
                        .type(PenaltyType.FIX)
                        .amount(remainingMonthDebt.multiply(BigDecimal.valueOf(0.01)))
                        .createdAt(todayDate)
                        .build()
                );
            }
        }

        LocalDate lastBalanceDate = lastBalance.getCreatedAt();
        BigDecimal dailyPercentAmount = balanceService.calculateDailyAccruedByPercentAmount(
                lastBalance.getRemainingDebt(), credit.getPercent()
        );
        BigDecimal newRemainingMonthDebt = lastBalance.getRemainingMonthDebt();
        if (isNewPaymentPeriod) {
            newRemainingMonthDebt = credit.getMonthAmount();
        }
        Balance newBalance = Balance.builder()
                .credit(credit)
                .remainingDebt(lastBalance.getRemainingDebt())
                .remainingMonthDebt(newRemainingMonthDebt)
                .accruedByPercent(dailyPercentAmount)
                .createdAt(lastBalanceDate.plusDays(1))
                .build();
        balanceRepository.save(newBalance);
    }
}
