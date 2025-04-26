package ru.improve.abs.info.service.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import ru.improve.abs.info.service.api.exception.CustomAuthEntryPoint;
import ru.improve.abs.info.service.core.security.AuthTokenFilter;
import ru.improve.abs.info.service.core.security.service.AuthService;

@EnableWebSecurity
@EnableMethodSecurity
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
                                .requestMatchers(HttpMethod.GET, "/graphql").permitAll()
                                .requestMatchers(HttpMethod.POST, "/graphql").permitAll()
                                .requestMatchers(HttpMethod.GET, "/graphiql").permitAll()
                                .requestMatchers(HttpMethod.POST, "/graphiql").permitAll()
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
