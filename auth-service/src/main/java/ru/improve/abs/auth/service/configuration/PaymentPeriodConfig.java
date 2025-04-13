package ru.improve.abs.auth.service.configuration;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Value
@ConfigurationProperties(prefix = "credit")
public class PaymentPeriodConfig {

    private Duration paymentDuration;
}
