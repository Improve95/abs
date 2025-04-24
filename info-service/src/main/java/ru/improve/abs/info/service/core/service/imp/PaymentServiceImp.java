package ru.improve.abs.info.service.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoaderEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.dto.PageableDto;
import ru.improve.abs.info.service.api.dto.payment.PaymentFilter;
import ru.improve.abs.info.service.api.dto.payment.PaymentRequest;
import ru.improve.abs.info.service.api.dto.payment.PaymentResponse;
import ru.improve.abs.info.service.core.mapper.PaymentMapper;
import ru.improve.abs.info.service.core.repository.PaymentRepository;
import ru.improve.abs.info.service.core.service.PaymentService;
import ru.improve.abs.info.service.model.Payment;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.improve.abs.info.service.uitl.GraphQlUtil.createSpecFromArguments;

@RequiredArgsConstructor
@Service
public class PaymentServiceImp implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentResponse> getPayments(PaymentRequest paymentRequest) {
        PageableDto pageableDto = paymentRequest.getPageableDto();
        Pageable page = PageRequest.of(pageableDto.getPageNumber(), pageableDto.getPageSize());
        PaymentFilter paymentFilter = paymentRequest.getPaymentFilter();

        Specification<Payment> paymentSpecification = Specification.where(null);
        paymentSpecification = createSpecFromArguments(paymentSpecification, paymentFilter);

        return paymentRepository.findAll(paymentSpecification, page).stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();
    }

    @Override
    public Map<Long, PaymentResponse> getBatchPayments(
            Set<Long> creditIds,
            BatchLoaderEnvironment batchLoaderEnvironment
    ) {
        PaymentRequest paymentRequest = (PaymentRequest) batchLoaderEnvironment.getKeyContextsList().getFirst();

        PaymentFilter paymentFilter = paymentRequest.getPaymentFilter();
        if (paymentFilter == null) {
            paymentFilter = new PaymentFilter();
        }
        paymentFilter.setCreditIds(creditIds);
        paymentRequest.setPaymentFilter(paymentFilter);
        return getPayments(paymentRequest).stream()
                .collect(Collectors.toMap(
                        PaymentResponse::getCreditId,
                        paymentResponse -> paymentResponse
                ));
    }
}
