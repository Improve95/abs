package ru.improve.abs.info.service.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.dto.payment.PaymentRequest;
import ru.improve.abs.info.service.api.dto.payment.PaymentResponse;
import ru.improve.abs.info.service.core.service.PaymentService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PaymentServiceImp implements PaymentService {

    @Override
    public List<PaymentResponse> getPayments(PaymentRequest paymentRequest) {
        return List.of(PaymentResponse.builder()
                .id(10)
                .build());
    }

    @Override
    public Map<Long, PaymentResponse> getBatchPayments(Set<Long> creditIds, PaymentRequest paymentRequest) {
        return Map.of();
    }
}
