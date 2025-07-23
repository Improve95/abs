package ru.improve.abs.service.core.service;


import ru.improve.abs.service.model.Session;
import ru.improve.abs.service.model.User;

import java.time.Instant;

public interface SessionService {

    Session create(User user, Instant issuedAt, Instant expiredAt);

    void disableSessionById(long id);

    void disableAllSessionByUser(User user);

    Session findSessionById(long id);
}
