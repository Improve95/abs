package ru.improve.abs.service.api.dto.credit;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CreditsProfitReportResponse {

    private double profit;

    private String recordDateText;
}
