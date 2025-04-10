package ru.improve.abs.auth.service.configuration.security.tokenConfig;

import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
@Configuration
public class CoderConfig {

     @Bean
     public SecretKey microserviceSecretKey(TokenConfig tokenConfig) {
          return new OctetSequenceKey.Builder(tokenConfig.getMicroserviceSecret().getBytes()).build().toSecretKey();
     }

     @Bean
     public JwtDecoder microserviceJwtDecoder(@Qualifier("microserviceSecretKey") SecretKey secretKey) {
          return NimbusJwtDecoder.withSecretKey(secretKey).build();
     }

     @Bean
     public JwtEncoder microserviceJwtEncoder(@Qualifier("microserviceSecretKey") SecretKey secretKey) {
          return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
     }

     @Bean
     public SecretKey clientSecretKey(TokenConfig tokenConfig) {
          return new OctetSequenceKey.Builder(tokenConfig.getClientSecret().getBytes()).build().toSecretKey();
     }

     @Bean
     public JwtDecoder clientJwtDecoder(@Qualifier("clientSecretKey") SecretKey secretKey) {
          return NimbusJwtDecoder.withSecretKey(secretKey).build();
     }

     @Bean
     public JwtEncoder clientJwtEncoder(@Qualifier("clientSecretKey") SecretKey secretKey) {
          return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
     }
}