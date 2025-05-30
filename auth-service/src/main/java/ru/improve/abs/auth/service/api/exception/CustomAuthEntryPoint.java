package ru.improve.abs.auth.service.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static ru.improve.abs.auth.service.api.exception.ErrorCode.INVALID_JWT_TOKEN;

@RequiredArgsConstructor
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        ServiceException serviceException = new ServiceException(INVALID_JWT_TOKEN, authException);
        handlerExceptionResolver.resolveException(request, response, null, serviceException);
    }
}
