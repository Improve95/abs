package ru.improve.abs.info.service.api.dto.payment;

import lombok.Builder;
import lombok.Data;
import ru.improve.abs.info.service.api.dto.PageableDto;

@Data
@Builder
public class PaymentRequest {

    private PageableDto pageableDto;

    private PaymentFilter paymentFilter;
}
