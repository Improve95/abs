package ru.improve.abs.info.service.core.security.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.exception.ServiceException;
import ru.improve.abs.info.service.core.security.service.TokenService;

import static ru.improve.abs.info.service.api.exception.ErrorCode.ILLEGAL_VALUE;
import static ru.improve.abs.info.service.uitl.MessageKeys.SESSION_TOKEN_INVALID;

@RequiredArgsConstructor
@Service
public class TokenServiceImp implements TokenService {

    private final JwtDecoder jwtDecoder;

    @Override
    public Jwt parseJwt(String jwtToken) {
        try {
            return jwtDecoder.decode(jwtToken);
        } catch (JwtException ex) {
            throw new ServiceException(ILLEGAL_VALUE, SESSION_TOKEN_INVALID, ex.getCause());
        }
    }
}

