package ru.improve.abs.info.service.api.dto.credit;

import lombok.Data;
import ru.improve.abs.info.service.api.dto.FilterType;
import ru.improve.abs.info.service.api.dto.FilterTypeEnum;
import ru.improve.abs.info.service.api.dto.credit.filter.InitialAmountBetweenFilter;
import ru.improve.abs.info.service.api.dto.credit.filter.TakingDateBetweenFilter;
import ru.improve.abs.info.service.model.credit.CreditStatus;

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
