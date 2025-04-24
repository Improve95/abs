package ru.improve.abs.info.service.api.dto.credit.filter;

import lombok.Data;
import ru.improve.abs.info.service.api.dto.DriftingFilter;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

@Data
public class TakingDateDriftingFilter implements DriftingFilter<ChronoLocalDate> {

    private LocalDate lte;

    private LocalDate gte;

    @Override
    public String getEntityFieldName() {
        return "takingDate";
    }

    @Override
    public Class<ChronoLocalDate> getComparableClass() {
        return ChronoLocalDate.class;
    }
}
