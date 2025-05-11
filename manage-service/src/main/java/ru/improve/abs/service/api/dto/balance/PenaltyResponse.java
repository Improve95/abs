package ru.improve.abs.service.api.dto.balance;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.improve.abs.service.model.penalty.PenaltyStatus;
import ru.improve.abs.service.model.penalty.PenaltyType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Jacksonized
public class PenaltyResponse {

    private long id;

    private long creditId;

    private PenaltyType type;

    private BigDecimal amount;

    private PenaltyStatus status;

    private LocalDate createdAt;
}
