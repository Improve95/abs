package ru.improve.abs.info.service.api.controller;

import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
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
import java.util.concurrent.CompletableFuture;

import static ru.improve.abs.info.service.uitl.GraphQlUtil.PAYMENT_DATA_LOADER;

@RequiredArgsConstructor
@Controller
public class GraphQlController {

    private final CreditService creditService;

    private final PaymentService paymentService;

    @QueryMapping
    public List<CreditResponse> credits(
            @Argument PageableDto page,
            @Argument CreditFilter filter
    ) {
        return creditService.getCredits(CreditRequest.builder()
                .pageableDto(page)
                .creditFilter(filter)
                .build()
        );
    }

    @SchemaMapping(field = "payments", typeName = "Credit")
    public CompletableFuture<List<PaymentResponse>> payments(
            CreditResponse creditResponse,
            @Argument PageableDto page,
            @Argument PaymentFilter filter,
            DataFetchingEnvironment env
    ) {
        DataLoader<Long, List<PaymentResponse>> dataLoader = env.getDataLoader(PAYMENT_DATA_LOADER);
        return dataLoader.load(
                creditResponse.getId(),
                PaymentRequest.builder()
                        .pageableDto(page)
                        .paymentFilter(filter)
                        .build()
        );
    }
}
