package ru.improve.abs.info.service.api.dto.credit.filter;

import lombok.Data;
import ru.improve.abs.info.service.api.dto.DriftingFilter;

import java.math.BigDecimal;

@Data
public class InitialAmountDriftingFilter implements DriftingFilter<BigDecimal> {

    private BigDecimal gte;

    private BigDecimal lte;

    @Override
    public String getEntityFieldName() {
        return "initialAmount";
    }

    @Override
    public Class<BigDecimal> getComparableClass() {
        return BigDecimal.class;
    }
}
