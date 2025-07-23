package ru.improve.abs.service.core.security.service.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import ru.improve.abs.service.api.dto.auth.login.LoginRequest;
import ru.improve.abs.service.api.dto.auth.login.LoginResponse;
import ru.improve.abs.service.api.dto.auth.refresh.RefreshAccessTokenResponse;
import ru.improve.abs.service.api.dto.auth.resetPassword.ResetPasswordGetLinkRequest;
import ru.improve.abs.service.api.dto.auth.resetPassword.ResetPasswordSendPasswordRequest;
import ru.improve.abs.service.api.dto.auth.signin.SignInRequest;
import ru.improve.abs.service.api.dto.auth.signin.SignInResponse;
import ru.improve.abs.service.api.exception.ServiceException;
import ru.improve.abs.service.configuration.security.SessionConfig;
import ru.improve.abs.service.core.mapper.AuthMapper;
import ru.improve.abs.service.core.mapper.UserMapper;
import ru.improve.abs.service.core.repository.PasswordResetRequestRepository;
import ru.improve.abs.service.core.repository.UserRepository;
import ru.improve.abs.service.core.security.UserDetailService;
import ru.improve.abs.service.core.security.service.AuthService;
import ru.improve.abs.service.core.security.service.TokenService;
import ru.improve.abs.service.core.service.SessionService;
import ru.improve.abs.service.core.service.UserService;
import ru.improve.abs.service.model.PasswordResetRequest;
import ru.improve.abs.service.model.Session;
import ru.improve.abs.service.model.User;
import ru.improve.abs.service.util.DatabaseUtil;
import ru.improve.abs.service.util.MessageUtil;
import ru.improve.abs.service.util.SecurityUtil;

import java.time.Duration;
import java.time.Instant;

import static ru.improve.abs.service.api.exception.ErrorCode.ALREADY_EXIST;
import static ru.improve.abs.service.api.exception.ErrorCode.EXPIRED;
import static ru.improve.abs.service.api.exception.ErrorCode.ILLEGAL_VALUE;
import static ru.improve.abs.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.improve.abs.service.api.exception.ErrorCode.NOT_FOUND;
import static ru.improve.abs.service.api.exception.ErrorCode.UNAUTHORIZED;
import static ru.improve.abs.service.configuration.security.tokenConfig.TokenCoderConfig.ACCESS_TOKEN_CODER;
import static ru.improve.abs.service.configuration.security.tokenConfig.TokenCoderConfig.REFRESH_TOKEN_CODER;
import static ru.improve.abs.service.configuration.security.tokenConfig.TokenCoderConfig.RESET_PASSWORD_TOKEN_CODER;
import static ru.improve.abs.service.util.MessageKeys.RESET_PASSWORD_MAIL_MESSAGE_TEXT;
import static ru.improve.abs.service.util.MessageKeys.RESET_PASSWORD_MAIL_SUBJECT;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImp implements AuthService {

    private final UserDetailService userDetailService;

    private final AuthenticationManager authManager;

    private final UserService userService;

    private final UserRepository userRepository;

    private final SessionService sessionService;

    private final PasswordResetRequestRepository passwordResetRequestRepository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    private final JavaMailSender emailSender;

    private final EntityManager em;

    private final MessageUtil messageUtil;

    private final SessionConfig sessionConfig;

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
            Session session = sessionService.findSessionById(sessionId);
            if (!session.isEnable()) {
                throw new ServiceException(EXPIRED, "session is disabled");
            }
            if (tokenService.checkTokenExpired(jwtToken)) {
                throw new ServiceException(EXPIRED, "access token");
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
            throw ex.getCode() == EXPIRED ? ex : new ServiceException(UNAUTHORIZED);
        }
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

        SignInResponse signInResponse = userMapper.toSignInUserResponse(user);
        LoginResponse loginResponse = login(
                LoginRequest.builder()
                        .login(signInRequest.getEmail())
                        .password(signInRequest.getPassword())
                        .build());
        signInResponse.setAccessToken(loginResponse.getAccessToken());
        signInResponse.setRefreshToken(loginResponse.getRefreshToken());
        return signInResponse;
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

        Instant issuedAt = Instant.now();
        Session session = sessionService.create(user, issuedAt, issuedAt.plus(sessionConfig.getRefreshDuration()));

        JwtClaimsSet claims = SecurityUtil.createDefaultClaims(
                user.getUsername(),
                issuedAt,
                issuedAt.plus(sessionConfig.getAccessDuration()),
                session.getId()
        );
        Jwt accessTokenJwt = tokenService.generateToken(claims, ACCESS_TOKEN_CODER);

        claims = SecurityUtil.createDefaultClaims(
                user.getUsername(),
                issuedAt,
                issuedAt.plus(sessionConfig.getRefreshDuration()),
                session.getId()
        );
        Jwt refreshTokenJwt = tokenService.generateToken(claims, REFRESH_TOKEN_CODER);

        LoginResponse loginResponse = authMapper.toLoginResponse(session);
        loginResponse.setAccessToken(accessTokenJwt.getTokenValue());
        loginResponse.setRefreshToken(refreshTokenJwt.getTokenValue());

        return loginResponse;
    }

    /*
    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnbWFpbDFAZ21haWwuY29tIiwic2Vzc2lvbklkIjoxMiwiZXhwIjoxNzU1ODYwMDgxLCJpYXQiOjE3NTMyNjgwODF9.yg096-4wg-xrBdJmcxUX2c7ckPNXMiqy0va9Y5NXll8
    * */
    @Override
    public RefreshAccessTokenResponse refreshAccessToken(String refreshToken) {
        try {
            Jwt jwtToken = tokenService.parseJwt(refreshToken, REFRESH_TOKEN_CODER);
            if (tokenService.checkTokenExpired(jwtToken)) {
                throw new ServiceException(EXPIRED, "refresh token");
            }

            long sessionId = tokenService.getSessionId(jwtToken);
            Session session = sessionService.findSessionById(sessionId);
            if (!session.isEnable()) {
                throw new ServiceException(EXPIRED, "session is disabled");
            }

            User user = userService.findUserByEmail(jwtToken.getSubject());

            Instant issuedAt = Instant.now();
            JwtClaimsSet claims = SecurityUtil.createDefaultClaims(
                    user.getUsername(),
                    issuedAt,
                    issuedAt.plus(sessionConfig.getAccessDuration()),
                    sessionId
            );

            Jwt accessToken = tokenService.generateToken(claims, ACCESS_TOKEN_CODER);
            return RefreshAccessTokenResponse.builder()
                    .accessToken(accessToken.getTokenValue())
                    .build();
        } catch (ServiceException ex) {
            throw ex.getCode() == EXPIRED ? ex : new ServiceException(UNAUTHORIZED);
        }
    }

    @Override
    public void logout() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
        long sessionId = Long.parseLong(details.getSessionId());
        sessionService.disableSessionById(sessionId);
    }

    @Override
    public void logoutAllSessions() {
        sessionService.disableAllSessionByUser(userService.getUserFromAuthentication());
    }

    @Transactional
    @Override
    public void sendLinkForResetPassword(ResetPasswordGetLinkRequest resetPasswordGetLinkRequest) {
        String email = resetPasswordGetLinkRequest.getEmail();
        User user = userService.findUserByEmail(email);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getUsername())
                .expiresAt(Instant.now().plus(Duration.ofMinutes(20)))
                .build();
        Jwt passwordResetToken = tokenService.generateToken(claims, RESET_PASSWORD_TOKEN_CODER);

        disableAllResetPasswordRequest(user);

        PasswordResetRequest passwordResetRequest = PasswordResetRequest.builder()
                .user(user)
                .token(passwordResetToken.getTokenValue())
                .isEnable(true)
                .build();
        passwordResetRequestRepository.save(passwordResetRequest);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(messageUtil.resolveMessage(RESET_PASSWORD_MAIL_SUBJECT));
        message.setTo(email);
        message.setText(messageUtil.resolveMessage(RESET_PASSWORD_MAIL_MESSAGE_TEXT, passwordResetToken.getTokenValue()));
        emailSender.send(message);
    }

    @Transactional
    @Override
    public void resetPassword(String token, ResetPasswordSendPasswordRequest resetPasswordSendPasswordRequest) {
        PasswordResetRequest passwordResetRequest = passwordResetRequestRepository.findByToken(token)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "reset request", "reset token"));

        if (!passwordResetRequest.isEnable()) {
            throw new ServiceException(EXPIRED, "reset token");
        }
        passwordResetRequest.setEnable(false);

        Jwt jwt;
        try {
            jwt = tokenService.parseJwt(token, RESET_PASSWORD_TOKEN_CODER);
        } catch (JwtException ex) {
            throw new ServiceException(ILLEGAL_VALUE, ex.getCause());
        }
        if (jwt.getExpiresAt().isBefore(Instant.now())) {
            throw new ServiceException(EXPIRED, "reset token");
        }
        String email = jwt.getSubject();
        User user = userService.findUserByEmail(email);
        sessionService.disableAllSessionByUser(user);
        user.setPassword(passwordEncoder.encode(resetPasswordSendPasswordRequest.getPassword()));
    }

    private void disableAllResetPasswordRequest(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<PasswordResetRequest> criteriaUpdate = cb.createCriteriaUpdate(PasswordResetRequest.class);
        Root<PasswordResetRequest> passwordResetRequestRoot = criteriaUpdate.getRoot();
        CriteriaUpdate<PasswordResetRequest> update = criteriaUpdate.set(passwordResetRequestRoot.get("isEnable"), false)
                .where(cb.equal(passwordResetRequestRoot.get("user"), user));
        em.createQuery(update).executeUpdate();
    }
}
