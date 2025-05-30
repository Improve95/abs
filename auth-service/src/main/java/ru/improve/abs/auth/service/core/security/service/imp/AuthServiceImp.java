package ru.improve.abs.auth.service.core.security.service.imp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import ru.improve.abs.auth.service.api.dto.auth.LoginRequest;
import ru.improve.abs.auth.service.api.dto.auth.LoginResponse;
import ru.improve.abs.auth.service.api.dto.auth.TokenExchangeResponse;
import ru.improve.abs.auth.service.api.dto.user.SignInRequest;
import ru.improve.abs.auth.service.api.dto.user.SignInResponse;
import ru.improve.abs.auth.service.api.exception.ServiceException;
import ru.improve.abs.auth.service.core.mapper.AuthMapper;
import ru.improve.abs.auth.service.core.mapper.UserMapper;
import ru.improve.abs.auth.service.core.repository.SessionRepository;
import ru.improve.abs.auth.service.core.repository.UserRepository;
import ru.improve.abs.auth.service.core.security.UserDetailService;
import ru.improve.abs.auth.service.core.security.service.AuthService;
import ru.improve.abs.auth.service.core.security.service.TokenService;
import ru.improve.abs.auth.service.core.service.SessionService;
import ru.improve.abs.auth.service.model.Session;
import ru.improve.abs.auth.service.model.User;
import ru.improve.abs.auth.service.util.DatabaseUtil;

import java.util.stream.Collectors;

import static ru.improve.abs.auth.service.api.exception.ErrorCode.ALREADY_EXIST;
import static ru.improve.abs.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.improve.abs.auth.service.api.exception.ErrorCode.INVALID_JWT_TOKEN;
import static ru.improve.abs.auth.service.api.exception.ErrorCode.NOT_FOUND;
import static ru.improve.abs.auth.service.api.exception.ErrorCode.SESSION_IS_OVER;
import static ru.improve.abs.auth.service.api.exception.ErrorCode.UNAUTHORIZED;
import static ru.improve.abs.auth.service.util.SecurityUtil.CLIENT_CODER;
import static ru.improve.abs.auth.service.util.SecurityUtil.MICROSERVICE_CODER;
import static ru.improve.abs.auth.service.util.SecurityUtil.ROLE_AUTHORITY_CLAIM;
import static ru.improve.abs.auth.service.util.SecurityUtil.SESSION_ID_CLAIM;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImp implements AuthService {

    private final UserDetailService userDetailService;

    private final AuthenticationManager authManager;

    private final UserRepository userRepository;

    private final SessionService sessionService;

    private final SessionRepository sessionRepository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

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

            UserDetails userDetails = userDetailService.loadUserByUsername(jwtToken.getSubject());
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

    @Transactional
    @Override
    public TokenExchangeResponse exchangeGatewayToken(String stringToken) {
        if (stringToken == null || !stringToken.startsWith("Bearer ")) {
            throw new ServiceException(INVALID_JWT_TOKEN);
        }
        Jwt jwtToken = tokenService.parseJwt(stringToken, CLIENT_CODER);
        long sessionId = tokenService.getSessionId(jwtToken);
        if (!sessionService.checkSessionEnableById(sessionId)) {
            throw new ServiceException(SESSION_IS_OVER);
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(jwtToken.getSubject());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .claim(SESSION_ID_CLAIM, sessionId)
                .claim(
                        ROLE_AUTHORITY_CLAIM,
                        userDetails.getAuthorities().stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","))
                )
                .build();
        Jwt exchangeJwtToken = tokenService.generateToken(claims, MICROSERVICE_CODER);

        return TokenExchangeResponse.builder()
                .token(exchangeJwtToken.getTokenValue())
                .build();
    }

    @Transactional
    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userMapper.toUser(signInRequest);
        user.setPassword(passwordEncoder.encode(signInRequest.getPassword()));

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            if (DatabaseUtil.isUniqueConstraintException(ex)) {
                throw new ServiceException(ALREADY_EXIST, "user", "email");
            }
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }

        return userMapper.toSignInUserResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication;
        if (!userRepository.existsByEmail(loginRequest.getLogin())) {
            throw new ServiceException(NOT_FOUND, "user", "login");
        }
        try {
            authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new ServiceException(UNAUTHORIZED, ex);
        }

        User user = (User) authentication.getPrincipal();
        if (user == null) {
            throw new ServiceException(UNAUTHORIZED);
        }

        Session session = sessionService.create(user);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getUsername())
                .issuedAt(session.getIssuedAt())
                .expiresAt(session.getExpiredAt())
                .claim(SESSION_ID_CLAIM, session.getId())
                .claim(
                        ROLE_AUTHORITY_CLAIM,
                        user.getAuthorities().stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","))
                )
                .build();
        Jwt accessTokenJwt = tokenService.generateToken(claims, MICROSERVICE_CODER);
        LoginResponse loginResponse = authMapper.toLoginResponse(session);
        loginResponse.setAccessToken(accessTokenJwt.getTokenValue());

        return loginResponse;
    }

    @Transactional
    @Override
    public void logout() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
        long sessionId = Long.parseLong(details.getSessionId());
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "session", "id"));
        session.setEnable(false);
    }
}
