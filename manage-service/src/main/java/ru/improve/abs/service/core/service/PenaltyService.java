package ru.improve.abs.service.core.service;


import ru.improve.abs.service.model.credit.Credit;

import java.math.BigDecimal;

public interface PenaltyService {

    BigDecimal editPenaltyAfterPayment(BigDecimal paymentAmount, Credit credit);
}
