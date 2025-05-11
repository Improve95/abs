package ru.improve.abs.service.api.dto.credit.graphql;

import lombok.Data;
import ru.improve.abs.service.api.dto.credit.graphql.filter.InitialAmountBetweenFilter;
import ru.improve.abs.service.api.dto.credit.graphql.filter.TakingDateBetweenFilter;
import ru.improve.abs.service.api.dto.graphql.FilterType;
import ru.improve.abs.service.api.dto.graphql.FilterTypeEnum;
import ru.improve.abs.service.model.credit.CreditStatus;

import java.util.List;

@Data
public class CreditFilter {

    @FilterType(type = FilterTypeEnum.EQUALS)
    private Long id;

    @FilterType(
            type = FilterTypeEnum.BETWEEN,
            fieldName = "initialAmount"
    )
    private InitialAmountBetweenFilter initialAmountFilter;

    @FilterType(
            type = FilterTypeEnum.BETWEEN,
            fieldName = "takingDate"
    )
    private TakingDateBetweenFilter takingDateFilter;

    @FilterType(type = FilterTypeEnum.EQUALS)
    private CreditStatus creditStatus;

    @FilterType(
            type = FilterTypeEnum.CONTAINS,
            referencedColumnName = "userId"
    )
    private List<Integer> userIds;
}
