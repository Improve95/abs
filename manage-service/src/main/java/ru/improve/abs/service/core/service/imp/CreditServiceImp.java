package ru.improve.abs.service.core.service.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.improve.abs.service.api.dto.credit.CreditRequestResponse;
import ru.improve.abs.service.api.dto.credit.CreditResponse;
import ru.improve.abs.service.api.dto.credit.CreditsProfitReportResponse;
import ru.improve.abs.service.api.dto.credit.GetExpiredCreditsReport;
import ru.improve.abs.service.api.dto.credit.GetLoansAmountOutputResponse;
import ru.improve.abs.service.api.dto.credit.PostCreditRequest;
import ru.improve.abs.service.api.dto.credit.PostCreditRequestRequest;
import ru.improve.abs.service.api.dto.credit.graphql.CreditFilter;
import ru.improve.abs.service.api.dto.graphql.PageableDto;
import ru.improve.abs.service.api.exception.ServiceException;
import ru.improve.abs.service.core.mapper.CreditMapper;
import ru.improve.abs.service.core.repository.CreditRepository;
import ru.improve.abs.service.core.repository.CreditRequestRepository;
import ru.improve.abs.service.core.repository.CreditTariffRepository;
import ru.improve.abs.service.core.repository.ReportViewRepository;
import ru.improve.abs.service.core.service.BalanceService;
import ru.improve.abs.service.core.service.CreditService;
import ru.improve.abs.service.core.service.UserService;
import ru.improve.abs.service.model.CreditRequest;
import ru.improve.abs.service.model.CreditTariff;
import ru.improve.abs.service.model.User;
import ru.improve.abs.service.model.credit.Credit;

import java.time.LocalDate;
import java.util.List;

import static ru.improve.abs.service.api.exception.ErrorCode.ILLEGAL_DTO_VALUE;
import static ru.improve.abs.service.api.exception.ErrorCode.NOT_FOUND;
import static ru.improve.abs.service.core.repository.request.CriteriaApiRequests.getExpiredCreditsReportRequest;
import static ru.improve.abs.service.core.repository.request.CriteriaApiRequests.getLoansAmountOutputReportRequest;
import static ru.improve.abs.service.util.GraphQlUtil.createSpecFromArguments;
import static ru.improve.abs.service.util.MessageKeys.ILLEGAL_CREDIT_REQUEST_DTO;

@RequiredArgsConstructor
@Service
public class CreditServiceImp implements CreditService {

    private final UserService userService;

    private final BalanceService balanceService;

    private final CreditRequestRepository creditRequestRepository;

    private final CreditTariffRepository creditTariffRepository;

    private final ReportViewRepository reportViewRepository;

    private final CreditRepository creditRepository;

    private final EntityManager em;

    private final CreditMapper creditMapper;

    @Override
    public List<CreditResponse> getCredits(ru.improve.abs.service.api.dto.credit.CreditRequest creditRequest) {
        PageableDto pageableDto = creditRequest.getPageableDto();
        Pageable page = PageRequest.of(pageableDto.getPageNumber(), pageableDto.getPageSize());
        CreditFilter creditFilter = creditRequest.getCreditFilter();

        Specification<Credit> creditSpecification = Specification.where(null);
        creditSpecification = createSpecFromArguments(creditSpecification, creditFilter);

        return creditRepository.findAll(creditSpecification, page).stream()
                .map(creditMapper::toCreditResponse)
                .toList();
    }

    @Transactional
    @Override
    public CreditRequestResponse createCreditRequest(PostCreditRequestRequest postCreditRequest) {
        User user = userService.getUserFromAuthentication();
        CreditTariff creditTariff = findCreditTariffById(postCreditRequest.getCreditTariffId());

        if (postCreditRequest.getCreditAmount().compareTo(creditTariff.getUpToAmount()) > 0 ||
                postCreditRequest.getCreditDuration() > creditTariff.getUpToCreditDuration()) {
            throw new ServiceException(ILLEGAL_DTO_VALUE, ILLEGAL_CREDIT_REQUEST_DTO);
        }

        CreditRequest creditRequest = creditMapper.toCreditRequest(postCreditRequest);
        creditRequest.setCreditTariff(creditTariff);
        creditRequest.setUser(user);

        creditRequest = creditRequestRepository.save(creditRequest);
        return creditMapper.toCreditRequestResponse(creditRequest);
    }

    @Transactional
    @Override
    public GetLoansAmountOutputResponse getLoansAmountOutputReport(LocalDate from, LocalDate to) {
        CriteriaQuery<GetLoansAmountOutputResponse> request = getLoansAmountOutputReportRequest(em, from, to);
        return em.createQuery(request).getSingleResult();
    }

    @Transactional
    @Override
    public double getExpiredCreditPercentRatio(LocalDate from, LocalDate to) {
        CriteriaQuery<GetExpiredCreditsReport> request = getExpiredCreditsReportRequest(em, from, to);
        GetExpiredCreditsReport getExpiredCreditsReport = em.createQuery(request).getSingleResult();
        long total = getExpiredCreditsReport.getTotalCredit();
        long expired = getExpiredCreditsReport.getExpiredCredit();
        return expired / (total / 100D);
    }

    @Transactional
    @Override
    public List<CreditsProfitReportResponse> getCreditsProfitReport() {
        return reportViewRepository.findAll().stream()
                .map(creditMapper::toCreditsProfitReportResponse)
                .toList();
    }

    @Transactional
    @Override
    public CreditResponse createCredit(PostCreditRequest creditRequest) {
        User user = userService.findUserById(creditRequest.getUserId());
        CreditTariff creditTariff = findCreditTariffById(creditRequest.getTariffId());

        Credit credit = creditMapper.toCredit(creditRequest);
        credit.setMonthAmount(balanceService.calculateCreditMonthAmount(
                creditRequest.getInitialAmount(), creditRequest.getPercent(), creditRequest.getCreditPeriod()
        ));
        credit.setUser(user);
        credit.setCreditTariff(creditTariff);
        credit = creditRepository.save(credit);

        return creditMapper.toCreditResponse(credit);
    }

    @Transactional
    public CreditTariff findCreditTariffById(int id) {
        return creditTariffRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "creditTariff", "id"));
    }

    @Transactional
    @Override
    public Credit findCreditById(long creditId) {
        return creditRepository.findById(creditId)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "credit", "id"));
    }
}
