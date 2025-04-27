package ru.improve.abs.service.configuration.graphql;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderOptions;
import org.dataloader.MappedBatchLoaderWithContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.improve.abs.service.api.dto.payment.PaymentResponse;
import ru.improve.abs.service.core.service.PaymentService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Configuration
public class DataLoaderConfig {

    @Bean
    public DataLoader<Long, List<PaymentResponse>> paymentDataLoader(PaymentService paymentService) {
        DataLoaderOptions dataLoaderOptions = DataLoaderOptions.newOptions()
                .setCachingEnabled(false);
        return DataLoaderFactory.newMappedDataLoader(
                paymentBatchLoader(paymentService),
                dataLoaderOptions
        );
    }

    private MappedBatchLoaderWithContext<Long, List<PaymentResponse>> paymentBatchLoader(PaymentService paymentService) {
        return (creditIds, batchLoaderEnvironment) -> CompletableFuture.supplyAsync(
                () -> paymentService.getBatchPayments(creditIds, batchLoaderEnvironment)
        );
    }
}
