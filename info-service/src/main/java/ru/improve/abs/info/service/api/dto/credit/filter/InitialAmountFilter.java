package ru.improve.abs.info.service.api.dto.credit.filter;

import lombok.Data;
import ru.improve.abs.info.service.api.dto.Filter;

import java.math.BigDecimal;

@Data
public class InitialAmountFilter implements Filter<BigDecimal> {

    private BigDecimal gt;

    private BigDecimal in;

    private BigDecimal lt;
}
