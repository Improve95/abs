package ru.improve.abs.info.service.core.service.imp;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.criteria.Subquery;
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
import ru.improve.abs.info.service.model.credit.Credit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.improve.abs.info.service.uitl.GraphQlUtil.FILTER_ARGUMENT;
import static ru.improve.abs.info.service.uitl.GraphQlUtil.PAGE_ARGUMENT;
import static ru.improve.abs.info.service.uitl.GraphQlUtil.createCreditSpecFromArguments;

@RequiredArgsConstructor
@Service
public class PaymentServiceImp implements PaymentService {

    private final Map<Long, PaymentResponse> responses = ImmutableMap.of(
            1L, PaymentResponse.builder()
                    .id(10)
                    .amount(BigDecimal.valueOf(100))
                    .build(),
            2L, PaymentResponse.builder()
                    .id(20)
                    .amount(BigDecimal.valueOf(200))
                    .build()
    );

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentResponse> getPayments(PaymentRequest paymentRequest) {
        PageableDto pageableDto = paymentRequest.getPageableDto();
        Pageable page = PageRequest.of(pageableDto.getPageNumber(), pageableDto.getPageSize());
        PaymentFilter paymentFilter = paymentRequest.getPaymentFilter();

        Specification<Payment> paymentSpecification = Specification.where(null);
        paymentSpecification = createCreditSpecFromArguments(paymentSpecification, paymentFilter);

        return paymentRepository.findAll(paymentSpecification, page).stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();
    }

    @Override
    public Map<Long, PaymentResponse> getBatchPayments(
            Set<Long> creditIds,
            BatchLoaderEnvironment batchLoaderEnvironment
    ) {
        DataFetchingEnvironment env = (DataFetchingEnvironment) batchLoaderEnvironment.getKeyContextsList().getFirst();
        PageableDto pageableDto = env.getArgument(PAGE_ARGUMENT);
        PaymentFilter paymentFilter = env.getArgument(FILTER_ARGUMENT);

        Pageable page = PageRequest.of(pageableDto.getPageNumber(), pageableDto.getPageSize());
        Specification<Payment> paymentSpecification = Specification.where(
                (root, query, criteriaBuilder) -> {
                    Subquery<Credit> subquery = query.subquery(Credit.class);
//                    Root<Credit> credit = subquery.from(Credit.class);
                    return criteriaBuilder.in(root.get("credit")).value(subquery);
                }
        );
        paymentSpecification = createCreditSpecFromArguments(paymentSpecification, paymentFilter);

        return paymentRepository.findAll(paymentSpecification, page).stream()
                .collect(Collectors.toMap(
                        payment -> payment.getCredit().getId(),
                        paymentMapper::toPaymentResponse
                ));
    }
}
