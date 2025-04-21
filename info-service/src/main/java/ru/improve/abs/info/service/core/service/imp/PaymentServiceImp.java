package ru.improve.abs.info.service.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.dto.payment.PaymentRequest;
import ru.improve.abs.info.service.api.dto.payment.PaymentResponse;
import ru.improve.abs.info.service.core.service.PaymentService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentServiceImp implements PaymentService {

    @Override
    public List<PaymentResponse> getPayments(PaymentRequest paymentRequest) {
        return List.of(PaymentResponse.builder()
                .id(10)
                .build());
    }
}
