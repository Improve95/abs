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

     public static final String PASSWORD_JWT_CODER = "password_coder";

     public static final String JWT_ENCODER = "_jwt_encoder";

     public static final String JWT_DECODER = "_jwt_decoder";

     private final TokenConfig tokenConfig;

     @Primary
     @Bean(name = ACCESS_TOKEN_CODER + JWT_DECODER)
     public JwtDecoder clientJwtDecoder() {
          return NimbusJwtDecoder.withSecretKey(secretKey(tokenConfig.getSecret())).build();
     }

     @Primary
     @Bean(name = ACCESS_TOKEN_CODER + JWT_ENCODER)
     public JwtEncoder clientJwtEncoder() {
          return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey(tokenConfig.getSecret())));
     }

     @Bean(name = PASSWORD_JWT_CODER + JWT_DECODER)
     public JwtDecoder passwordResetJwtDecoder() {
          return NimbusJwtDecoder.withSecretKey(secretKey(tokenConfig.getSecret())).build();
     }

     @Bean(name = PASSWORD_JWT_CODER + JWT_ENCODER)
     public JwtEncoder passwordResetJwtEncoder() {
          return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey(tokenConfig.getSecret())));
     }

     private SecretKey secretKey(String secretKey) {
          return new OctetSequenceKey.Builder(secretKey.getBytes()).build().toSecretKey();
     }
}
