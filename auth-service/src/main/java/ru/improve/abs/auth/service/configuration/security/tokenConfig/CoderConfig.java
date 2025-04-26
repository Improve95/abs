package ru.improve.abs.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;

import static ru.improve.abs.auth.service.util.SecurityUtil.CLIENT_CODER;
import static ru.improve.abs.auth.service.util.SecurityUtil.JWT_DECODER;
import static ru.improve.abs.auth.service.util.SecurityUtil.JWT_ENCODER;
import static ru.improve.abs.auth.service.util.SecurityUtil.MICROSERVICE_CODER;

@RequiredArgsConstructor
@Configuration
public class CoderConfig {

     @Primary
     @Bean(name = MICROSERVICE_CODER + JWT_DECODER)
     public JwtDecoder microserviceJwtDecoder(TokenConfig tokenConfig) {
          return NimbusJwtDecoder.withSecretKey(clientSecretKey(tokenConfig.getMicroserviceSecret())).build();
     }

     @Primary
     @Bean(name = MICROSERVICE_CODER + JWT_ENCODER)
     public JwtEncoder microserviceJwtEncoder(TokenConfig tokenConfig) {
          return new NimbusJwtEncoder(new ImmutableSecret<>(clientSecretKey(tokenConfig.getMicroserviceSecret())));
     }

     @Bean(name = CLIENT_CODER + JWT_DECODER)
     public JwtDecoder clientJwtDecoder(TokenConfig tokenConfig) {
          return NimbusJwtDecoder.withSecretKey(clientSecretKey(tokenConfig.getClientSecret())).build();
     }

     @Bean(name = CLIENT_CODER + JWT_ENCODER)
     public JwtEncoder clientJwtEncoder(TokenConfig tokenConfig) {
          return new NimbusJwtEncoder(new ImmutableSecret<>(clientSecretKey(tokenConfig.getClientSecret())));
     }

     private SecretKey clientSecretKey(String secret) {
          return new OctetSequenceKey.Builder(secret.getBytes()).build().toSecretKey();
     }
}