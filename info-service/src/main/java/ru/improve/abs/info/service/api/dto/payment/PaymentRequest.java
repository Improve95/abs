package ru.improve.abs.info.service.api.dto.payment;

import lombok.Builder;
import lombok.Data;
import ru.improve.abs.info.service.api.dto.PageableDto;

import java.util.Set;

@Data
@Builder
public class PaymentRequest {

    private PageableDto pageableDto;

    private PaymentFilter paymentFilter;

    @Builder.Default
    private Set<Long> creditIds = Set.of();
}
