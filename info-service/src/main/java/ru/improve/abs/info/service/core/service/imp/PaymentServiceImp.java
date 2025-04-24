package ru.improve.abs.info.service.core.service.imp;

import com.google.common.collect.ImmutableMap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Join;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final EntityManager em;

    @Override
    public List<PaymentResponse> getPayments(PaymentRequest paymentRequest) {
        PageableDto pageableDto = paymentRequest.getPageableDto();
        Pageable page = PageRequest.of(pageableDto.getPageNumber(), pageableDto.getPageSize());
        PaymentFilter paymentFilter = paymentRequest.getPaymentFilter();
        Set<Long> creditIds = paymentRequest.getCreditIds();

        Specification<Payment> paymentSpecification = Specification.where(null);
        if (!creditIds.isEmpty()) {
            paymentSpecification = paymentSpecification.and(
                    ((root, query, criteriaBuilder) -> {
                        Join<Object, Object> credit = root.join("credit");
                        return criteriaBuilder.in(credit.get("id")).value(creditIds);
                    })
            );
        }

        /*Specification<Payment> paymentSpecification = Specification.where(null);
        paymentSpecification = Specification.where(
                (root, query, criteriaBuilder) -> {
                    Subquery<Credit> subquery = query.subquery(Credit.class);
//                    Root<Credit> credit = subquery.from(Credit.class);
                    return criteriaBuilder.in(root.get("credit")).value(subquery);
                }
        );*/

        /*Specification.where(
                ((root, query, criteriaBuilder) -> {
                    CriteriaBuilder.In<Credit> inClause = criteriaBuilder.in(root.get("credit"));
                    for (Long creditId : creditIds) {
                        inClause.value(title);
                    }
                    criteriaQuery.select(root).where(inClause);
                    criteriaQuery.select(root)
                            .where(root.get("title")
                                    .in(titles));
                    Subquery<Department> subquery = criteriaQuery.subquery(Department.class);
                    Root<Department> dept = subquery.from(Department.class);
                    subquery.select(dept)
                            .distinct(true)
                            .where(criteriaBuilder.like(dept.get("name"), "%" + searchKey + "%"));

                    criteriaQuery.select(emp)
                            .where(criteriaBuilder.in(emp.get("department")).value(subquery));
                })
        );*/

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
        PaymentRequest paymentRequest = (PaymentRequest) batchLoaderEnvironment.getKeyContextsList().getFirst();
        paymentRequest.setCreditIds(creditIds);
        return getPayments(paymentRequest).stream()
                .collect(Collectors.toMap(
                        PaymentResponse::getCreditId,
                        paymentResponse -> paymentResponse
                ));
    }
}
