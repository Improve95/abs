package ru.improve.abs.service.core.service.imp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.abs.service.api.exception.ServiceException;
import ru.improve.abs.service.configuration.security.SessionConfig;
import ru.improve.abs.service.core.repository.SessionRepository;
import ru.improve.abs.service.core.service.SessionService;
import ru.improve.abs.service.model.Session;
import ru.improve.abs.service.model.User;

import java.time.Instant;
import java.util.List;

import static ru.improve.abs.service.api.exception.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class SessionServiceImp implements SessionService {

    private final SessionRepository sessionRepository;

    private final SessionConfig sessionConfig;

    @Transactional
    @Override
    public Session create(User user) {
//        setUserSessionDisable(user);
        Session session = Session.builder()
                .expiredAt(Instant.now().plus(sessionConfig.getDuration()))
                .user(user)
                .build();
        sessionRepository.save(session);
        return session;
    }

    @Transactional
    @Override
    public boolean checkSessionEnableById(long id) {
        Session session = sessionRepository.findById(id).orElseThrow(
                () -> new ServiceException(NOT_FOUND, "session", "id"));

        return checkSessionEnable(session);
    }

    @Override
    public boolean checkSessionEnable(Session session) {
        Instant nowTime = Instant.now();
        if (nowTime.isAfter(session.getExpiredAt())) {
            session.setEnable(false);
        }
        return session.isEnable();
    }

    @Transactional
    @Override
    public void disableSessionById(long id) {
        findSessionById(id).setEnable(false);
    }

    @Transactional
    @Override
    public void disableAllSessionByUser(User user) {
        List<Session> sessions = sessionRepository.findAllByUserAndIsEnable(user, true);
        sessions.forEach(this::disableSession);
    }

    @Transactional
    @Override
    public Session findSessionById(long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "session", "id"));
    }

    private void disableSession(Session session) {
        session.setEnable(false);
    }
}
