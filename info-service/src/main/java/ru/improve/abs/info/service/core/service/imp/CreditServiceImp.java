package ru.improve.abs.info.service.core.service.imp;

import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.dto.CreditResponse;
import ru.improve.abs.info.service.core.mapper.CreditMapper;
import ru.improve.abs.info.service.core.repository.CreditRepository;
import ru.improve.abs.info.service.core.service.CreditService;
import ru.improve.abs.info.service.model.credit.Credit;
import ru.improve.abs.info.service.uitl.QueryUtil;

import java.util.List;

import static ru.improve.abs.info.service.uitl.QueryUtil.CREDIT_STATUS;
import static ru.improve.abs.info.service.uitl.QueryUtil.CREDIT_TARIFF;
import static ru.improve.abs.info.service.uitl.QueryUtil.ID;
import static ru.improve.abs.info.service.uitl.QueryUtil.PAGE_NUMBER;
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
    public List<CreditResponse> getCredits(DataFetchingEnvironment environment) {
        int pageSize = environment.getArgument(PAGE_SIZE);
        int pageNumber = environment.getArgument(PAGE_NUMBER);
        Pageable page = PageRequest.of(pageNumber, pageSize);

        var argumentsMap = environment.getArguments();
        argumentsMap.remove(REMOVABLE_ARGUMENTS);

        Specification<Credit> creditSpecification = argumentsMap.entrySet().stream()
                .<Specification<Credit>>map(QueryUtil::addEqualsSpec)
                .reduce(Specification.where(null), Specification::and);

        return creditRepository.findAll(creditSpecification, page).stream()
                .map(creditMapper::toCreditResponse)
                .toList();
    }
}
