package ru.improve.abs.service.core.service.imp;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoaderEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.improve.abs.service.api.dto.graphql.PageableDto;
import ru.improve.abs.service.api.dto.payment.GetTotalPaymentsResponse;
import ru.improve.abs.service.api.dto.payment.PaymentFilter;
import ru.improve.abs.service.api.dto.payment.PaymentRequest;
import ru.improve.abs.service.api.dto.payment.PaymentResponse;
import ru.improve.abs.service.api.dto.payment.PostPaymentRequest;
import ru.improve.abs.service.api.dto.payment.PostPaymentResponse;
import ru.improve.abs.service.core.mapper.BalanceMapper;
import ru.improve.abs.service.core.mapper.PaymentMapper;
import ru.improve.abs.service.core.repository.PaymentRepository;
import ru.improve.abs.service.core.service.BalanceService;
import ru.improve.abs.service.core.service.CreditService;
import ru.improve.abs.service.core.service.PaymentService;
import ru.improve.abs.service.core.service.PenaltyService;
import ru.improve.abs.service.model.Payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.improve.abs.service.core.repository.request.CriteriaApiRequests.getTotalPaymentsResponseRequest;
import static ru.improve.abs.service.util.GraphQlUtil.createSpecFromArguments;

@RequiredArgsConstructor
@Service
public class PaymentServiceImp implements PaymentService {

    private final CreditService creditService;

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final BalanceMapper balanceMapper;

    private final PenaltyService penaltyService;

    private final BalanceService balanceService;

    private final EntityManager em;

    @Transactional
    @Override
    public PostPaymentResponse payForCredit(PostPaymentRequest postPaymentRequest) {
        Payment payment = balanceMapper.toPayment(postPaymentRequest);
        payment.setCreatedAt(Instant.now());
        payment.setCredit(creditService.findCreditById(postPaymentRequest.getCreditId()));

        payment = paymentRepository.save(payment);

        BigDecimal remainPaymentAmount = penaltyService.editPenaltyAfterPayment(
                payment.getAmount(),
                payment.getCredit()
        );
        balanceService.editBalanceAfterPayment(remainPaymentAmount, payment.getCredit());

        return balanceMapper.toPostPaymentResponse(payment);
    }

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
    public Map<Long, List<PaymentResponse>> getBatchPayments(
            Set<Long> creditIds,
            BatchLoaderEnvironment batchLoaderEnvironment
    ) {
        int keyListSize = batchLoaderEnvironment.getKeyContextsList().size();
        PaymentRequest paymentRequest = (PaymentRequest) batchLoaderEnvironment.getKeyContextsList()
                .get(keyListSize - 1);
        PaymentFilter paymentFilter = paymentRequest.getPaymentFilter();
        if (paymentFilter == null) {
            paymentFilter = new PaymentFilter();
        }
        paymentFilter.setCreditIds(creditIds);
        paymentRequest.setPaymentFilter(paymentFilter);
        return getPayments(paymentRequest).stream()
                .collect(Collectors.groupingBy(
                        PaymentResponse::getCreditId
                ));
    }

    @Transactional
    @Override
    public GetTotalPaymentsResponse getTotalPaymentsResponse(LocalDate from, LocalDate to) {
        return em.createQuery(getTotalPaymentsResponseRequest(em, from, to)).getSingleResult();
    }
}
