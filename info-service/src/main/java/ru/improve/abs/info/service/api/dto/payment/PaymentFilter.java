package ru.improve.abs.info.service.api.dto.payment;

import lombok.Data;
import ru.improve.abs.info.service.api.dto.FilterType;
import ru.improve.abs.info.service.api.dto.FilterTypeEnum;

import java.util.Set;

@Data
public class PaymentFilter {

    @FilterType(type = FilterTypeEnum.EQUALS)
    private Long id;

    @FilterType(type = FilterTypeEnum.JOIN_ENTITY_ID_CONTAINS)
    private Set<Long> creditIds;
}
