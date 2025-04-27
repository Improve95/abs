package ru.improve.abs.service.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.abs.service.api.dto.credit.CreditRequestResponse;
import ru.improve.abs.service.api.dto.credit.CreditResponse;
import ru.improve.abs.service.api.dto.credit.CreditTariffResponse;
import ru.improve.abs.service.api.dto.credit.PostCreditRequest;
import ru.improve.abs.service.api.dto.credit.PostCreditRequestRequest;
import ru.improve.abs.service.model.CreditRequest;
import ru.improve.abs.service.model.CreditTariff;
import ru.improve.abs.service.model.credit.Credit;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = MapperUtil.class
)
public interface CreditMapper {

    CreditRequest toCreditRequest(PostCreditRequestRequest creditRequestRequest);

    CreditTariffResponse toCreditTariffResponse(CreditTariff creditTariff);

    @Mapping(
            target = "creditTariffId",
            expression = "java(creditRequest.getCreditTariff().getId())"
    )
    @Mapping(
            target = "userId",
            expression = "java(creditRequest.getUser().getId())"
    )
    CreditRequestResponse toCreditRequestResponse(CreditRequest creditRequest);


    Credit toCredit(PostCreditRequest creditRequest);

    @Mapping(
            target = "creditTariffId",
            expression = "java(credit.getCreditTariff().getId())"
    )
    @Mapping(
            target = "userId",
            expression = "java(credit.getUser().getId())"
    )
    CreditResponse toCreditResponse(Credit credit);
}
