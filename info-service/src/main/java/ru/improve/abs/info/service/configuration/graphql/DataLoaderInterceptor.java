package ru.improve.abs.info.service.configuration.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoaderInterceptor /*implements WebGraphQlInterceptor*/ {

    /*private final DataLoader<Long, List<PaymentResponse>> paymentDataLoader;

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, WebGraphQlInterceptor.Chain chain) {
        DataLoaderRegistry registry = new DataLoaderRegistry();
        registry.register(PAYMENT_DATA_LOADER, paymentDataLoader);

        request.configureExecutionInput((executionInput, builder) ->
                builder.dataLoaderRegistry(registry).build()
        );

        return chain.next(request);
    }*/
}
