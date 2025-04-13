package ru.improve.abs.auth.service.configuration.security;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Value
@ConfigurationProperties(prefix = "security.session", ignoreUnknownFields = false)
public class SessionConfig {

    private Duration duration;
}
