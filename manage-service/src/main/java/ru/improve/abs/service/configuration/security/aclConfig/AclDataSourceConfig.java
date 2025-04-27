package ru.improve.abs.service.configuration.security.aclConfig;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "spring.acl-datasource", ignoreUnknownFields = false)
public class AclDataSourceConfig {

    private String url;

    private String username;

    private String password;

    private String driverClassName;
}
