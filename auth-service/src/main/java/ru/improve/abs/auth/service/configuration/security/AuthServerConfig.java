package ru.improve.abs.auth.service.configuration.security;

import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;

@Configuration
public class AuthServerConfig {

     @Bean
     public JwtDecoder jwtDecoder(SecretKey secretKey) {
          return NimbusJwtDecoder.withSecretKey(secretKey).build();
     }

     @Bean
     public JwtEncoder jwtEncoder(SecretKey secretKey) {
          return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
     }

     @Bean
     public SecretKey secretKey(TokenConfig tokenConfig) {
          return new OctetSequenceKey.Builder(tokenConfig.getClientSecret().getBytes()).build().toSecretKey();
     }

     /*@Bean
     public RegisteredClientRepository registeredClientRepository(ClientConfig clientConfig, TokenConfig tokenConfig) {
          RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                  .clientId(clientConfig.getId())
                  .clientSecret(tokenConfig.getClientSecret())
                  .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                  .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                  .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                  .redirectUri("http://127.0.0.1:8080/login/oauth2/code/gateway")
                  .scope(OidcScopes.OPENID)
                  .build();
          return new InMemoryRegisteredClientRepository(registeredClient);
     }

     @Bean
     public JWKSource<SecurityContext> jwkSource() {
          RSAKey rsaKey = generateRsa();
          JWKSet jwkSet = new JWKSet(rsaKey);
          return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
     }

     private static RSAKey generateRsa() {
          KeyPair keyPair = generateRsaKey();
          RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
          RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
          return new RSAKey.Builder(publicKey)
                  .privateKey(privateKey)
                  .keyID(UUID.randomUUID().toString())
                  .build();
     }

     private static KeyPair generateRsaKey() {
          KeyPair keyPair;
          try {
               KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
               keyPairGenerator.initialize(2048);
               keyPair = keyPairGenerator.generateKeyPair();
          } catch (Exception ex) {
               throw new IllegalStateException(ex);
          }
          return keyPair;
     }*/

     /*@Bean
     public ProviderSettings providerSettings() {
          return ProviderSettings.builder()
                  .issuer("http://localhost:9000")
                  .build();
     }*/
}