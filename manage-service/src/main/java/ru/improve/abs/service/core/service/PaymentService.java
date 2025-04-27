package ru.improve.abs.service.core.service;

import org.dataloader.BatchLoaderEnvironment;
import ru.improve.abs.service.api.dto.payment.PaymentRequest;
import ru.improve.abs.service.api.dto.payment.PaymentResponse;
import ru.improve.abs.service.api.dto.payment.PostPaymentRequest;
import ru.improve.abs.service.api.dto.payment.PostPaymentResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PaymentService {

    List<PaymentResponse> getPayments(PaymentRequest paymentRequest);

    Map<Long, List<PaymentResponse>> getBatchPayments(Set<Long> creditIds, BatchLoaderEnvironment env);

    PostPaymentResponse payForCredit(PostPaymentRequest postPaymentRequest);
}
