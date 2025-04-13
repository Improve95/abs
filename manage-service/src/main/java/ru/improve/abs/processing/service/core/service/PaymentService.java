package ru.improve.abs.processing.service.core.service;

import ru.improve.abs.processing.service.api.dto.payment.PostPaymentRequest;
import ru.improve.abs.processing.service.api.dto.payment.PostPaymentResponse;

public interface PaymentService {

    PostPaymentResponse payForCredit(PostPaymentRequest postPaymentRequest);
}
