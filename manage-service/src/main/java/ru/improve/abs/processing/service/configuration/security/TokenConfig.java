package ru.improve.abs.processing.service.configuration.security;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "app.token", ignoreUnknownFields = false)
public class TokenConfig {

    private String secret;
}
