package ru.improve.abs.gateway.service.core.security.service.imp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import ru.improve.abs.gateway.service.api.exception.ServiceException;
import ru.improve.abs.gateway.service.core.security.service.AuthService;
import ru.improve.abs.gateway.service.core.security.service.TokenService;
import ru.improve.abs.gateway.service.core.service.SessionService;

import static ru.improve.abs.gateway.service.api.exception.ErrorCode.SESSION_IS_OVER;
import static ru.improve.abs.gateway.service.api.exception.ErrorCode.UNAUTHORIZED;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImp implements AuthService {

    private final TokenService tokenService;

    private final SessionService sessionService;

    private final UserDetailsService userDetailsService;

    @Override
    public boolean setAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken)) {
            log.info("Not authenticated request: {}: {}", request.getMethod(), request.getRequestURL());
            return true;
        }

        try {
            Jwt jwtToken = (Jwt) auth.getPrincipal();

            long sessionId = tokenService.getSessionId(jwtToken);
            if (!sessionService.checkSessionEnableById(sessionId)) {
                throw new ServiceException(SESSION_IS_OVER);
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtToken.getSubject());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetails(request.getRemoteAddr(), String.valueOf(sessionId)));
            securityContext.setAuthentication(authentication);
            return true;
        } catch (ServiceException ex) {
            securityContext.setAuthentication(null);
            SecurityContextHolder.clearContext();
            response.reset();

            ServiceException exception = (ex.getCode() == SESSION_IS_OVER ?
                    new ServiceException(SESSION_IS_OVER) :
                    new ServiceException(UNAUTHORIZED));
            throw exception;
        }
    }
}
