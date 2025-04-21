package ru.improve.abs.info.service.api.controller;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.dto.PageableDto;
import ru.improve.abs.info.service.api.dto.credit.CreditFilter;
import ru.improve.abs.info.service.api.dto.credit.CreditRequest;
import ru.improve.abs.info.service.api.dto.credit.CreditResponse;
import ru.improve.abs.info.service.api.dto.payment.PaymentFilter;
import ru.improve.abs.info.service.api.dto.payment.PaymentRequest;
import ru.improve.abs.info.service.api.dto.payment.PaymentResponse;
import ru.improve.abs.info.service.core.service.CreditService;
import ru.improve.abs.info.service.core.service.PaymentService;

import java.util.List;

@GraphQLApi
@RequiredArgsConstructor
@Service
public class GraphQlController {

    private final CreditService creditService;

    private final PaymentService paymentService;

//    @QueryMapping(name = "credit")
    @GraphQLQuery
    public List<CreditResponse> credits(
            @GraphQLArgument(name = "page") @GraphQLNonNull PageableDto pageableDto,
            @GraphQLArgument(name = "filter") CreditFilter creditFilter
    ) {
        return creditService.getCredits(CreditRequest.builder()
                .pageableDto(pageableDto)
                .creditFilter(creditFilter)
                .build()
        );
    }

    @GraphQLQuery
    public List<PaymentResponse> payments(
            @GraphQLContext CreditResponse creditResponse,
            @GraphQLArgument(name = "page") PageableDto pageableDto,
            @GraphQLArgument(name = "filter") PaymentFilter paymentFilter
    ) {
        return paymentService.getPayments(PaymentRequest.builder()
                .pageableDto(pageableDto)
                .paymentFilter(paymentFilter)
                .build()
        );
    }
}
