package ru.improve.abs.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.abs.api.dto.balance.BalanceResponse;
import ru.improve.abs.api.dto.balance.PostBalanceRequest;
import ru.improve.abs.api.dto.payment.PostPaymentRequest;
import ru.improve.abs.api.dto.payment.PostPaymentResponse;
import ru.improve.abs.model.Balance;
import ru.improve.abs.model.Payment;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = MapperUtil.class
)
public interface BalanceMapper {

    Payment toPayment(PostPaymentRequest postPaymentRequest);

    PostPaymentResponse toPostPaymentResponse(Payment payment);

    Balance toBalance(PostBalanceRequest postBalanceRequest);

    @Mapping(
            target = "creditId",
            expression = "java(balance.getCredit().getId())")
    BalanceResponse toBalanceResponse(Balance balance);
}
