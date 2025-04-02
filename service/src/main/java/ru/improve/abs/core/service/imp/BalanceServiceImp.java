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
import ru.improve.abs.core.service.BalanceService;
import ru.improve.abs.model.Balance;
import ru.improve.abs.model.credit.Credit;
import ru.improve.abs.model.credit.CreditStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static ru.improve.abs.api.exception.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class BalanceServiceImp implements BalanceService {

    private final BalanceRepository balanceRepository;

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
    public BalanceResponse editBalanceAfterPayment(BigDecimal paymentAmount, Credit credit) {
        Balance lastBalance = findLastCreditBalance(credit);
        BigDecimal remainingDebt = lastBalance.getRemainingDebt();
        BigDecimal remainingMonthDebt = lastBalance.getRemainingMonthDebt();
        BigDecimal diffPaymentAndMonthBalance = remainingMonthDebt.subtract(paymentAmount);
        if (diffPaymentAndMonthBalance.compareTo(BigDecimal.ZERO) <= 0) {
            if (remainingMonthDebt.compareTo(BigDecimal.ZERO) <= 0) {
                lastBalance.setRemainingDebt(remainingDebt.subtract(paymentAmount));
            } else {
                diffPaymentAndMonthBalance = paymentAmount.subtract(remainingMonthDebt);
                lastBalance.setRemainingDebt(remainingDebt.subtract(diffPaymentAndMonthBalance));
            }
        }
        lastBalance.setRemainingMonthDebt(remainingMonthDebt.subtract(paymentAmount));
        if (lastBalance.getRemainingDebt().compareTo(BigDecimal.ZERO) <= 0) {
            lastBalance.getCredit().setCreditStatus(CreditStatus.CLOSE);
        }
        return balanceMapper.toBalanceResponse(lastBalance);
    }

    @Transactional
    @Override
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

