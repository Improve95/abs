package ru.improve.abs.service.core.security.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import ru.improve.abs.service.api.exception.ServiceException;
import ru.improve.abs.service.core.security.service.TokenService;
import ru.improve.abs.service.util.SecurityUtil;

import java.time.Instant;
import java.util.Map;

import static ru.improve.abs.service.api.exception.ErrorCode.UNAUTHORIZED;
import static ru.improve.abs.service.configuration.security.tokenConfig.TokenCoderConfig.JWT_DECODER;
import static ru.improve.abs.service.configuration.security.tokenConfig.TokenCoderConfig.JWT_ENCODER;

@RequiredArgsConstructor
@Service
public class TokenServiceImp implements TokenService {

    private final Map<String, JwtDecoder> jwtDecoders;

    private final Map<String, JwtEncoder> jwtEncoders;

    @Override
    public Jwt generateToken(JwtClaimsSet claims, String encoderType) {
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        JwtEncoder jwtEncoder = jwtEncoders.get(encoderType + JWT_ENCODER);
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
    }

    @Override
    public long getSessionId(Jwt jwt) {
        return jwt.getClaim(SecurityUtil.SESSION_ID_CLAIM);
    }

    @Override
    public boolean checkTokenExpired(Jwt jwt) {
        return jwt.getExpiresAt().isBefore(Instant.now());
    }

    @Override
    public Jwt parseJwt(String jwt, String encoderType) {
        JwtDecoder jwtDecoder = jwtDecoders.get(encoderType + JWT_DECODER);
        try {
            return jwtDecoder.decode(jwt);
        } catch (JwtException jwtException) {
            throw new ServiceException(UNAUTHORIZED);
        }
    }
}
