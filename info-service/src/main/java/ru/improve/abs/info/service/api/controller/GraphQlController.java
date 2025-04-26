package ru.improve.abs.info.service.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.improve.abs.info.service.core.service.CreditService;
import ru.improve.abs.info.service.core.service.PaymentService;

@RequiredArgsConstructor
@Controller
public class GraphQlController {

    private final CreditService creditService;

    private final PaymentService paymentService;

    /*@QueryMapping
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
    }*/
}
