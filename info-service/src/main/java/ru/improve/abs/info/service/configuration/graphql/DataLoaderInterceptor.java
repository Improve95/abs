package ru.improve.abs.info.service.configuration.graphql;

import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.improve.abs.info.service.api.dto.payment.PaymentResponse;

import static ru.improve.abs.info.service.uitl.GraphQlUtil.PAYMENT_DATA_LOADER;

@RequiredArgsConstructor
@Component
public class DataLoaderInterceptor implements WebGraphQlInterceptor{

    private final DataLoader<Long, PaymentResponse> paymentDataLoader;

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, WebGraphQlInterceptor.Chain chain) {
        DataLoaderRegistry registry = new DataLoaderRegistry();
        registry.register(PAYMENT_DATA_LOADER, paymentDataLoader);

        request.configureExecutionInput((executionInput, builder) ->
                builder.dataLoaderRegistry(registry).build()
        );

        return chain.next(request);
    }
}
