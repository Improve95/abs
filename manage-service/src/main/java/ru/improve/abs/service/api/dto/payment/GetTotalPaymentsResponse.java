package ru.improve.abs.service.api.dto.payment;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class GetTotalPaymentsResponse {

    private BigDecimal totalAmount;
}
