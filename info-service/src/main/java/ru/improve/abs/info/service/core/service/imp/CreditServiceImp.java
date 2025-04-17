package ru.improve.abs.info.service.core.service.imp;

import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.dto.CreditResponse;
import ru.improve.abs.info.service.core.mapper.CreditMapper;
import ru.improve.abs.info.service.core.repository.CreditRepository;
import ru.improve.abs.info.service.core.service.CreditService;

import java.util.List;

import static ru.improve.abs.info.service.uitl.QueryUtil.PAGE_NUMBER;
import static ru.improve.abs.info.service.uitl.QueryUtil.PAGE_SIZE;

@RequiredArgsConstructor
@Service
public class CreditServiceImp implements CreditService {

    private final CreditRepository creditRepository;

    private final CreditMapper creditMapper;

    @Override
    public List<CreditResponse> getCredits(DataFetchingEnvironment environment) {
        int pageSize = environment.getArgument(PAGE_SIZE);
        int pageNumber = environment.getArgument(PAGE_NUMBER);
        Pageable page = PageRequest.of(pageNumber, pageSize);

        var argumentsMap = environment.getArguments();
//        Specification<Credit> creditSpec = hasEqualField("creditStatus", CreditStatus.OPEN);

        return creditRepository.findAll(page).stream()
                .map(creditMapper::toCreditResponse)
                .toList();
    }
}
