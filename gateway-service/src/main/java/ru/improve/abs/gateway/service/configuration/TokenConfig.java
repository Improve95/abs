package ru.improve.abs.gateway.service.configuration;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "app.token")
public class TokenConfig {

    private String secret;
}
