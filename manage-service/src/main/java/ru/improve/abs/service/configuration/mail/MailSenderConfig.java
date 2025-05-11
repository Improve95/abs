package ru.improve.abs.service.configuration.mail;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;

@Value
@ConfigurationProperties(prefix = "spring.mail")
public class MailSenderConfig {

    String host;

    int port;

    String username;

    String password;

    LinkedHashMap<String, Object> properties;
}
