package ru.improve.abs.auth.service.core.security.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import ru.improve.abs.auth.service.api.exception.ServiceException;
import ru.improve.abs.auth.service.core.security.service.TokenService;
import ru.improve.abs.auth.service.model.Session;

import static ru.improve.abs.auth.service.api.exception.ErrorCode.ILLEGAL_VALUE;
import static ru.improve.abs.auth.service.util.SecurityUtil.SESSION_ID_CLAIM;
import static ru.improve.abs.auth.service.util.message.MessageKeys.SESSION_TOKEN_INVALID;

@RequiredArgsConstructor
@Service
public class TokenServiceImp implements TokenService {

    private final JwtEncoder microserviceJwtEncoder;

    private final JwtDecoder microserviceJwtDecoder;

    private final JwtEncoder clientJwtEncoder;

    private final JwtDecoder clientJwtDecoder;

    @Override
    public Jwt generateToken(UserDetails userDetails, Session session) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .issuedAt(session.getIssuedAt())
                .expiresAt(session.getExpiredAt())
                .claim(SESSION_ID_CLAIM, session.getId())
                .build();
        return generateToken(claims);
    }

    @Override
    public Jwt generateToken(JwtClaimsSet claims) {
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
    }

    @Override
    public long getSessionId(Jwt jwt) {
        return jwt.getClaim(SESSION_ID_CLAIM);
    }

    @Override
    public Jwt parseJwt(String jwt) {
        try {
            return jwtDecoder.decode(jwt);
        } catch (JwtException ex) {
            throw new ServiceException(ILLEGAL_VALUE, SESSION_TOKEN_INVALID, ex.getCause());
        }
    }
}
