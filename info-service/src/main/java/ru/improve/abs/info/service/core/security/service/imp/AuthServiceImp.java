package ru.improve.abs.info.service.core.security.service.imp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.exception.ServiceException;
import ru.improve.abs.info.service.core.security.service.AuthService;
import ru.improve.abs.info.service.core.security.service.TokenService;

import java.util.Arrays;
import java.util.Map;

import static ru.improve.abs.info.service.api.exception.ErrorCode.UNAUTHORIZED;
import static ru.improve.abs.info.service.uitl.SecurityUtil.ROLE_AUTHORITY_CLAIM;
import static ru.improve.abs.info.service.uitl.SecurityUtil.SESSION_ID_CLAIM;
import static ru.improve.abs.info.service.uitl.SecurityUtil.USER_ID_CLAIM;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImp implements AuthService {

    private final TokenService tokenService;

    @Override
    public boolean setAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        System.out.println("check request auth " + request.getMethod() + " " + request.getRequestURL().toString());
        try {
            if (!(auth instanceof JwtAuthenticationToken authToken)) {
                System.out.println("request not auth " + request.getMethod() + " " + request.getRequestURL().toString());
//            log.info("Not authenticated request: {}: {}", request.getMethod(), request.getRequestURL());
//                securityContext.setAuthentication(null);
                return true;
            }
            Jwt jwtToken = tokenService.parseJwt(authToken.getToken().getTokenValue());
            Map<String, Object> claims = jwtToken.getClaims();
            var rolesList = Arrays.stream(((String) claims.get(ROLE_AUTHORITY_CLAIM)).split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            UsernamePasswordAuthenticationToken setAuth = new UsernamePasswordAuthenticationToken(
                    claims.get(USER_ID_CLAIM),
                    null,
                    rolesList
            );
            setAuth.setDetails(new WebAuthenticationDetails(
                    request.getRemoteAddr(),
                    String.valueOf(claims.get(SESSION_ID_CLAIM))
            ));
            securityContext.setAuthentication(setAuth);
        } catch (ServiceException ex) {
            securityContext.setAuthentication(null);
            SecurityContextHolder.clearContext();
            response.reset();
            throw new ServiceException(UNAUTHORIZED, ex);
        }
        return true;
    }
}
