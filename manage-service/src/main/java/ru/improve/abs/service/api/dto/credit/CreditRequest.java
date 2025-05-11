package ru.improve.abs.service.api.dto.credit;

import lombok.Builder;
import lombok.Data;
import ru.improve.abs.service.api.dto.credit.graphql.CreditFilter;
import ru.improve.abs.service.api.dto.graphql.PageableDto;

@Data
@Builder
public class CreditRequest {

    private PageableDto pageableDto;

    private CreditFilter creditFilter;
}
