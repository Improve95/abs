package ru.improve.abs.service.api.dto.payment;

import lombok.Data;
import ru.improve.abs.service.api.dto.graphql.FilterType;
import ru.improve.abs.service.api.dto.graphql.FilterTypeEnum;

import java.util.Set;

@Data
public class PaymentFilter {

    @FilterType(type = FilterTypeEnum.EQUALS)
    private Long id;

    @FilterType(
            type = FilterTypeEnum.JOIN_ENTITY_ID_CONTAINS,
            mappedBy = "credit",
            referencedColumnName = "id"
    )
    private Set<Long> creditIds;
}
