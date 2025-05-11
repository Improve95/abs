package ru.improve.abs.service.api.dto.payment;

import lombok.Builder;
import lombok.Data;
import ru.improve.abs.service.api.dto.graphql.PageableDto;

@Data
@Builder
public class PaymentRequest {

    private PageableDto pageableDto;

    private PaymentFilter paymentFilter;
}
