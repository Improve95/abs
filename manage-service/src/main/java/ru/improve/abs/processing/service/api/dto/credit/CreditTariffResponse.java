package ru.improve.abs.processing.service.api.dto.credit;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class CreditTariffResponse {

    private int id;

    private String type;

    private BigDecimal upToAmount;

    private long upToCreditDuration;

    private int creditPercent;
}
