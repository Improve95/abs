package ru.improve.abs.processing.service.core.service;


import ru.improve.abs.processing.service.model.credit.Credit;

import java.math.BigDecimal;

public interface PenaltyService {

    BigDecimal editPenaltyAfterPayment(BigDecimal paymentAmount, Credit credit);
}
