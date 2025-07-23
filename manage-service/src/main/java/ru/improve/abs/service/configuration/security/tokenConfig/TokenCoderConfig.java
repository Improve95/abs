package ru.improve.abs.service.configuration.security.tokenConfig;

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

@RequiredArgsConstructor
@Configuration
public class TokenCoderConfig {

     public static final String ACCESS_TOKEN_CODER = "access_token_coder";

     public static final String REFRESH_TOKEN_CODER = "refresh_token_coder";

     public static final String RESET_PASSWORD_TOKEN_CODER = "reset_password_token_coder";

     public static final String JWT_ENCODER = "_jwt_encoder";

     public static final String JWT_DECODER = "_jwt_decoder";

     private final TokenConfig tokenConfig;

     @Primary
     @Bean(name = ACCESS_TOKEN_CODER + JWT_DECODER)
     public JwtDecoder clientJwtDecoder() {
          return NimbusJwtDecoder.withSecretKey(secretKey(tokenConfig.getAccessSecret())).build();
     }

     @Primary
     @Bean(name = ACCESS_TOKEN_CODER + JWT_ENCODER)
     public JwtEncoder clientJwtEncoder() {
          return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey(tokenConfig.getAccessSecret())));
     }

     @Bean(name = REFRESH_TOKEN_CODER + JWT_DECODER)
     public JwtDecoder refreshJwtDecoder() {
          return NimbusJwtDecoder.withSecretKey(secretKey(tokenConfig.getRefreshSecret())).build();
     }

     @Bean(name = REFRESH_TOKEN_CODER + JWT_ENCODER)
     public JwtEncoder refreshJwtEncoder() {
          return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey(tokenConfig.getRefreshSecret())));
     }

     @Bean(name = RESET_PASSWORD_TOKEN_CODER + JWT_DECODER)
     public JwtDecoder passwordResetJwtDecoder() {
          return NimbusJwtDecoder.withSecretKey(secretKey(tokenConfig.getResetPasswordSecret())).build();
     }

     @Bean(name = RESET_PASSWORD_TOKEN_CODER + JWT_ENCODER)
     public JwtEncoder passwordResetJwtEncoder() {
          return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey(tokenConfig.getResetPasswordSecret())));
     }

     private SecretKey secretKey(String secretKey) {
          return new OctetSequenceKey.Builder(secretKey.getBytes()).build().toSecretKey();
     }
}
