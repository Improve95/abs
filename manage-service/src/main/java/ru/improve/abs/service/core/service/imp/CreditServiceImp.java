package ru.improve.abs.service.core.service.imp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.improve.abs.service.api.dto.credit.CreditFilter;
import ru.improve.abs.service.api.dto.credit.CreditRequestResponse;
import ru.improve.abs.service.api.dto.credit.CreditResponse;
import ru.improve.abs.service.api.dto.credit.PostCreditRequest;
import ru.improve.abs.service.api.dto.credit.PostCreditRequestRequest;
import ru.improve.abs.service.api.dto.graphql.PageableDto;
import ru.improve.abs.service.api.exception.ServiceException;
import ru.improve.abs.service.core.mapper.CreditMapper;
import ru.improve.abs.service.core.repository.CreditRepository;
import ru.improve.abs.service.core.repository.CreditRequestRepository;
import ru.improve.abs.service.core.repository.CreditTariffRepository;
import ru.improve.abs.service.core.service.BalanceService;
import ru.improve.abs.service.core.service.CreditService;
import ru.improve.abs.service.core.service.UserService;
import ru.improve.abs.service.model.CreditRequest;
import ru.improve.abs.service.model.CreditTariff;
import ru.improve.abs.service.model.User;
import ru.improve.abs.service.model.credit.Credit;

import java.util.List;

import static ru.improve.abs.service.api.exception.ErrorCode.ILLEGAL_DTO_VALUE;
import static ru.improve.abs.service.api.exception.ErrorCode.NOT_FOUND;
import static ru.improve.abs.service.util.GraphQlUtil.createSpecFromArguments;
import static ru.improve.abs.service.util.MessageKeys.ILLEGAL_CREDIT_REQUEST_DTO;

@RequiredArgsConstructor
@Service
public class CreditServiceImp implements CreditService {

    private final UserService userService;

    private final BalanceService balanceService;

    private final CreditRequestRepository creditRequestRepository;

    private final CreditTariffRepository creditTariffRepository;

    private final CreditRepository creditRepository;

    private final CreditMapper creditMapper;

    /*@Transactional
    @Override
    public List<CreditTariffResponse> getAllCreditTariffs() {
        return creditTariffRepository.findAll().stream()
                .map(creditMapper::toCreditTariffResponse)
                .toList();
    }*/

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
/*
    @Transactional
    @Override
    public List<CreditResponse> getAllCredits(int pageNumber, int pageSize) {
        return creditRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream()
                .map(creditMapper::toCreditResponse)
                .toList();
    }

    @Transactional
    @Override
    public CreditResponse getCreditById(long creditId) {
        return creditMapper.toCreditResponse(findCreditById(creditId));
    }

    @Transactional
    @Override
    public List<CreditResponse> getAllCreditsByUserId(int userId, int pageNumber, int pageSize) {
        User user = userService.findUserById(userId);
        return creditRepository.findAllByUser(user, PageRequest.of(pageNumber, pageSize)).stream()
                .map(creditMapper::toCreditResponse)
                .toList();
    }*/

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

    /*@Transactional
    @Override
    public CreditResponse takeCreatedCredit(long creditId) {
        Credit credit = creditRepository.findCreditByIdAndAndCreditStatus(creditId, CreditStatus.CREATE)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "credit", "id and open status"));

        User user = credit.getUser();
        if (creditRepository.countCreditByUserAndCreditStatus(user, CreditStatus.OPEN) >= 1) {
            throw new ServiceException(ACCESS_DENIED, "you already have credit");
        }
        if (user.getId() != userService.getUserFromAuthentication().getId()) {
            throw new ServiceException(ACCESS_DENIED, "credit");
        }
        credit.setTakingDate(LocalDate.now());
        credit.setCreditStatus(CreditStatus.OPEN);
        balanceService.createBalance(PostBalanceRequest.builder()
                .credit(credit)
                .remainingDebt(credit.getInitialAmount())
                .remainingMonthDebt(credit.getMonthAmount())
                .accruedByPercent(balanceService.calculateDailyAccruedByPercentAmount(
                        credit.getInitialAmount(),
                        credit.getPercent()
                ))
                .build()
        );
        return creditMapper.toCreditResponse(credit);
    }*/

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
