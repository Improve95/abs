package ru.improve.abs.info.service.api.dto.credit;

import lombok.Data;
import ru.improve.abs.info.service.api.dto.credit.filter.InitialAmountDriftingFilter;
import ru.improve.abs.info.service.model.credit.CreditStatus;

@Data
public class CreditFilter {

    private Long id;

    private CreditStatus creditStatus;

    private InitialAmountDriftingFilter initialAmountFilter;

    private Integer userId;
}
