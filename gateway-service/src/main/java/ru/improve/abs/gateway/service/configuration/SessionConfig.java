package ru.improve.abs.gateway.service.configuration;


import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Value
@ConfigurationProperties(prefix = "app.session")
public class SessionConfig {

    private Duration duration;
}
