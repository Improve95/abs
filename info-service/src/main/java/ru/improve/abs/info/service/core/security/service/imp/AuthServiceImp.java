package ru.improve.abs.info.service.core.security.service.imp;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.core.security.service.AuthService;
import ru.improve.abs.info.service.core.security.service.TokenService;

import java.util.List;
import java.util.Map;

import static ru.improve.abs.info.service.uitl.SecurityUtil.USER_ID_CLAIM;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImp implements AuthService {

    private final TokenService tokenService;

    @Override
    public boolean setAuthentication(HttpServletRequest httpRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken authToken)) {
            securityContext.setAuthentication(null);
            return true;
        }
        Jwt jwtToken = tokenService.parseJwt(authToken.toString());
        Map<String, Object> claims = jwtToken.getClaims();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                claims.get(USER_ID_CLAIM), null, List.of(new SimpleGrantedAuthority("USER_ADMIN"))
        ));
        return true;
    }
}
