package ru.improve.abs.info.service.configuration.graphql;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoaderConfig {

    /*@Bean
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
    }*/
}
