package ru.improve.abs.auth.service.configuration.security;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "security.token", ignoreUnknownFields = false)
public class TokenConfig {

    String clientSecret;

    String gatewaySecret;
}
