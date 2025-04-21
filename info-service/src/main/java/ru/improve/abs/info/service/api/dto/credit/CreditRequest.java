package ru.improve.abs.info.service.api.dto.credit;

import lombok.Builder;
import lombok.Data;
import ru.improve.abs.info.service.api.dto.PageableDto;

@Data
@Builder
public class CreditRequest {

    private PageableDto pageableDto;

    private CreditFilter creditFilter;
}
