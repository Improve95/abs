package ru.improve.abs.auth.service.configuration.security;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "spring.acl-datasource", ignoreUnknownFields = false)
public class AclDataSourceConfig {

//    private String url;
//
//    private String username;
//
//    private String password;
//
//    private String driverClassName;
}
