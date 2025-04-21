package ru.improve.abs.info.service.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.abs.info.service.api.dto.credit.CreditResponse;
import ru.improve.abs.info.service.model.credit.Credit;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CreditMapper {

    CreditResponse toCreditResponse(Credit credit);
}
