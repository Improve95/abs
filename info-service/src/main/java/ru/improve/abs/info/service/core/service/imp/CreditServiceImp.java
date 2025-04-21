package ru.improve.abs.info.service.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.dto.PageableDto;
import ru.improve.abs.info.service.api.dto.credit.CreditRequest;
import ru.improve.abs.info.service.api.dto.credit.CreditResponse;
import ru.improve.abs.info.service.core.mapper.CreditMapper;
import ru.improve.abs.info.service.core.repository.CreditRepository;
import ru.improve.abs.info.service.core.service.CreditService;

import java.math.BigDecimal;
import java.util.List;

import static ru.improve.abs.info.service.uitl.QueryUtil.CREDIT_STATUS;
import static ru.improve.abs.info.service.uitl.QueryUtil.CREDIT_TARIFF;
import static ru.improve.abs.info.service.uitl.QueryUtil.ID;
import static ru.improve.abs.info.service.uitl.QueryUtil.PAGE_SIZE;
import static ru.improve.abs.info.service.uitl.QueryUtil.USER_ID;

@RequiredArgsConstructor
@Service
public class CreditServiceImp implements CreditService {

    private final CreditRepository creditRepository;

    private final CreditMapper creditMapper;

    private final List<String> REMOVABLE_ARGUMENTS = List.of(
            PAGE_SIZE, PAGE_SIZE
    );

    private final List<String> equalsArgument = List.of(
            ID, USER_ID, CREDIT_STATUS, CREDIT_TARIFF
    );

    @Override
    public List<CreditResponse> getCredits(CreditRequest creditRequest) {
        PageableDto pageableDto = creditRequest.getPageableDto();
        Pageable page = PageRequest.of(pageableDto.getPageNumber(), pageableDto.getPageSize());
//        Specification<Credit> creditSpecification = argumentsMap.entrySet().stream()
//                .<Specification<Credit>>map(QueryUtil::addEqualsSpec)
//                .reduce(Specification.where(null), Specification::and);

//        return creditRepository.findAll(page).stream()
//                .map(creditMapper::toCreditResponse)
//                .toList();
        return List.of(
                CreditResponse.builder()
                        .id(1)
                        .percent(10)
                        .initialAmount(BigDecimal.valueOf(9854.321))
                        .build()
        );
    }
}
