package ru.improve.abs.core.service;

import ru.improve.abs.model.credit.Credit;

import java.math.BigDecimal;

public interface PenaltyService {

    BigDecimal editPenaltyAfterPayment(BigDecimal paymentAmount, Credit credit);
}
