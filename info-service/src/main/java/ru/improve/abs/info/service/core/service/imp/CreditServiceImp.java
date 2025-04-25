package ru.improve.abs.info.service.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.dto.PageableDto;
import ru.improve.abs.info.service.api.dto.credit.CreditFilter;
import ru.improve.abs.info.service.api.dto.credit.CreditRequest;
import ru.improve.abs.info.service.api.dto.credit.CreditResponse;
import ru.improve.abs.info.service.core.mapper.CreditMapper;
import ru.improve.abs.info.service.core.repository.CreditRepository;
import ru.improve.abs.info.service.core.service.CreditService;
import ru.improve.abs.info.service.model.credit.Credit;

import java.util.List;

import static ru.improve.abs.info.service.uitl.GraphQlUtil.createSpecFromArguments;

@RequiredArgsConstructor
@Service
public class CreditServiceImp implements CreditService {

    private final CreditRepository creditRepository;

    private final CreditMapper creditMapper;

    @Override
    public List<CreditResponse> getCredits(CreditRequest creditRequest) {
        PageableDto pageableDto = creditRequest.getPageableDto();
        Pageable page = PageRequest.of(pageableDto.getPageNumber(), pageableDto.getPageSize());
        CreditFilter creditFilter = creditRequest.getCreditFilter();

        Specification<Credit> creditSpecification = Specification.where(null);
        creditSpecification = createSpecFromArguments(creditSpecification, creditFilter);

        return creditRepository.findAll(creditSpecification, page).stream()
                .map(creditMapper::toCreditResponse)
                .toList();
    }
}
