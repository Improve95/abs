package ru.improve.abs.info.service.core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.improve.abs.info.service.core.security.service.AuthService;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (authService.setAuthentication(request)) {
            filterChain.doFilter(request, response);
        }
    }
}
