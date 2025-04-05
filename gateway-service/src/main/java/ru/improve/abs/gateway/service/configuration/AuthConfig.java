package ru.improve.abs.gateway.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import ru.improve.abs.gateway.service.api.exception.CustomAuthEntryPoint;
import ru.improve.abs.gateway.service.core.security.AuthTokenFilter;
import ru.improve.abs.gateway.service.core.security.service.AuthService;

import static ru.improve.abs.gateway.service.api.ApiPaths.AUTH;
import static ru.improve.abs.gateway.service.api.ApiPaths.LOGIN;
import static ru.improve.abs.gateway.service.api.ApiPaths.SIGN_IN;

@Configuration
public class AuthConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthService authService,
            CustomAuthEntryPoint customAuthEntryPoint
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.POST, AUTH + SIGN_IN).permitAll()
                                .requestMatchers(HttpMethod.POST, AUTH + LOGIN).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(conf -> conf
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(customAuthEntryPoint)
                )
                .addFilterAfter(new AuthTokenFilter(authService), BearerTokenAuthenticationFilter.class);
        return http.build();
    }
}
