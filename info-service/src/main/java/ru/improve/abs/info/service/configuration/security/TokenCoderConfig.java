package ru.improve.abs.info.service.configuration.security;

import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
@Configuration
public class TokenCoderConfig {

    private final TokenConfig tokenConfig;

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey(tokenConfig.getMicroserviceSecret())));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(secretKey(tokenConfig.getMicroserviceSecret())).build();
    }

    private SecretKey secretKey(String secretKey) {
        return new OctetSequenceKey.Builder(secretKey.getBytes()).build().toSecretKey();
    }
}
