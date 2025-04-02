package ru.improve.abs.core.service.imp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.improve.abs.core.repository.PenaltyRepository;
import ru.improve.abs.core.service.PenaltyService;
import ru.improve.abs.model.credit.Credit;
import ru.improve.abs.model.penalty.Penalty;
import ru.improve.abs.model.penalty.PenaltyStatus;
import ru.improve.abs.model.penalty.PenaltyType;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PenaltyServiceImp implements PenaltyService {

    private final PenaltyRepository penaltyRepository;

    private final List<PenaltyType> checkPenaltyTypes = List.of(
            PenaltyType.MAIN_DEBT_REMAIN,
            PenaltyType.FIX,
            PenaltyType.PENNY
    );

    @Transactional
    @Override
    public BigDecimal editPenaltyAfterPayment(BigDecimal paymentAmount, Credit credit) {
        for (PenaltyType penaltyType : checkPenaltyTypes) {
            Pageable page = PageRequest.of(0, Integer.MAX_VALUE);
            Page<Penalty> penaltyPage = penaltyRepository.findAllByCreditAndTypeAndStatus(
                    credit,
                    penaltyType,
                    PenaltyStatus.NOT_PAID,
                    page
            );
            for (Penalty penalty : penaltyPage) {
                paymentAmount = processingPenalty(penalty, paymentAmount);
            }
        }
        return paymentAmount;
    }

    @Transactional
    public BigDecimal processingPenalty(Penalty penalty, BigDecimal paymentAmount) {
        BigDecimal penaltyAmount = penalty.getAmount();
        BigDecimal diffPenaltyAndPaymentAmount = paymentAmount.subtract(penaltyAmount);
        if (diffPenaltyAndPaymentAmount.compareTo(BigDecimal.ZERO) >= 0) {
            penalty.setAmount(BigDecimal.ZERO);
            penalty.setStatus(PenaltyStatus.PAID);
            return diffPenaltyAndPaymentAmount;
        } else {
            penalty.setAmount(penaltyAmount.subtract(paymentAmount));
            return BigDecimal.ZERO;
        }
    }
}
