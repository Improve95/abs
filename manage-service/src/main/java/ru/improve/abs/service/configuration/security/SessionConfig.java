package ru.improve.abs.service.configuration.security;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Value
@ConfigurationProperties(prefix = "app.session", ignoreUnknownFields = false)
public class SessionConfig {

    Duration accessDuration;

    Duration refreshDuration;
}
