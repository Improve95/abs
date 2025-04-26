package ru.improve.abs.info.service.core.service;

import org.dataloader.BatchLoaderEnvironment;
import ru.improve.abs.info.service.api.dto.payment.PaymentRequest;
import ru.improve.abs.info.service.api.dto.payment.PaymentResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PaymentService {

    List<PaymentResponse> getPayments(PaymentRequest paymentRequest);

    Map<Long, List<PaymentResponse>> getBatchPayments(Set<Long> creditIds, BatchLoaderEnvironment env);
}
