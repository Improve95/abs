package ru.improve.abs.info.service.core.service;

import ru.improve.abs.info.service.api.dto.payment.PaymentRequest;
import ru.improve.abs.info.service.api.dto.payment.PaymentResponse;

import java.util.List;

public interface PaymentService {

    List<PaymentResponse> getPayments(PaymentRequest paymentRequest);
}
