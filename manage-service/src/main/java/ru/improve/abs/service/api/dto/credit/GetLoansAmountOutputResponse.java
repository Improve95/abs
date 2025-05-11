package ru.improve.abs.service.api.dto.credit;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class GetLoansAmountOutputResponse {

    private long creditCount;

    private BigDecimal sumLoansAmountOutput;

    private Double avgLoansAmountOutput;
}
