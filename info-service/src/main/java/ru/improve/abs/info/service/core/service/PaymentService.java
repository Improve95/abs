package ru.improve.abs.info.service.core.service;

import ru.improve.abs.info.service.api.dto.payment.PaymentRequest;
import ru.improve.abs.info.service.api.dto.payment.PaymentResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PaymentService {

    List<PaymentResponse> getPayments(PaymentRequest paymentRequest);

    Map<Long, PaymentResponse> getBatchPayments(Set<Long> creditIds, PaymentRequest paymentRequest);
}
