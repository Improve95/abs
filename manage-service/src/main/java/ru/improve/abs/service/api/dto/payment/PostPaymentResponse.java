package ru.improve.abs.service.api.dto.payment;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PostPaymentResponse {

    private long id;
}
