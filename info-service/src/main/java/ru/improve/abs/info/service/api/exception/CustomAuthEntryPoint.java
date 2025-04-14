package ru.improve.abs.info.service.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static ru.improve.abs.info.service.api.exception.ErrorCode.UNAUTHORIZED;

@RequiredArgsConstructor
@Service
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        ServiceException serviceException = new ServiceException(UNAUTHORIZED, authException);
        handlerExceptionResolver.resolveException(request, response, null, serviceException);
    }
}
