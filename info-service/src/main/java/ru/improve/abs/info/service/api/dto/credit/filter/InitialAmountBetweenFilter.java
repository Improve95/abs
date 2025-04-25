package ru.improve.abs.info.service.api.dto.credit.filter;

import lombok.Data;
import ru.improve.abs.info.service.api.dto.BetweenFilter;

import java.math.BigDecimal;

@Data
public class InitialAmountBetweenFilter implements BetweenFilter<BigDecimal> {

    private BigDecimal gte;

    private BigDecimal lte;

    @Override
    public Class<BigDecimal> getComparableClass() {
        return BigDecimal.class;
    }
}
