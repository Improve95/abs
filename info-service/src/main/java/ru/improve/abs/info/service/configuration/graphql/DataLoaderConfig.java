package ru.improve.abs.info.service.configuration.graphql;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.MappedBatchLoaderWithContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.improve.abs.info.service.api.dto.payment.PaymentResponse;
import ru.improve.abs.info.service.core.service.PaymentService;

import java.util.concurrent.CompletableFuture;

@Configuration
public class DataLoaderConfig {

    @Bean
    public DataLoader<Long, PaymentResponse> paymentDataLoader(PaymentService paymentService) {
        return DataLoaderFactory.newMappedDataLoader(paymentBatchLoader(paymentService));
    }

    private MappedBatchLoaderWithContext<Long, PaymentResponse> paymentBatchLoader(PaymentService paymentService) {
        return (creditIds, batchLoaderEnvironment) -> CompletableFuture.supplyAsync(
                () -> paymentService.getBatchPayments(creditIds, batchLoaderEnvironment)
        );
    }
}
