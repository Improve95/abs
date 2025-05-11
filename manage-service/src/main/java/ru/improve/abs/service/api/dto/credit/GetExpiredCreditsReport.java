package ru.improve.abs.service.api.dto.credit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetExpiredCreditsReport {

    private long totalCredit;

    private long expiredCredit;
}
