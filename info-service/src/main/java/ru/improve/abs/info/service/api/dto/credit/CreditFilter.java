package ru.improve.abs.info.service.api.dto.credit;

import lombok.Data;
import ru.improve.abs.info.service.api.dto.FilterType;
import ru.improve.abs.info.service.api.dto.FilterTypeEnum;
import ru.improve.abs.info.service.api.dto.credit.filter.InitialAmountDriftingFilter;
import ru.improve.abs.info.service.api.dto.credit.filter.TakingDateDriftingFilter;
import ru.improve.abs.info.service.model.credit.CreditStatus;

import java.util.List;

@Data
public class CreditFilter {

    @FilterType(type = FilterTypeEnum.EQUALS)
    private Long id;

    @FilterType(type = FilterTypeEnum.BETWEEN)
    private InitialAmountDriftingFilter initialAmountFilter;

    @FilterType(type = FilterTypeEnum.BETWEEN)
    private TakingDateDriftingFilter takingDateFilter;

    @FilterType(type = FilterTypeEnum.EQUALS)
    private CreditStatus creditStatus;

    @FilterType(type = FilterTypeEnum.CONTAINS)
    private List<Integer> userIds;
}
