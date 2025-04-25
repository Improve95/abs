package ru.improve.abs.info.service.api.dto.credit.filter;

import lombok.Data;
import ru.improve.abs.info.service.api.dto.BetweenFilter;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

@Data
public class TakingDateBetweenFilter implements BetweenFilter<ChronoLocalDate> {

    private LocalDate lte;

    private LocalDate gte;

    @Override
    public Class<ChronoLocalDate> getComparableClass() {
        return ChronoLocalDate.class;
    }
}
