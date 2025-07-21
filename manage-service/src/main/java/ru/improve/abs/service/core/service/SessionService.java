package ru.improve.abs.service.core.service;


import ru.improve.abs.service.model.Session;
import ru.improve.abs.service.model.User;

public interface SessionService {

    Session create(User user);

    boolean checkSessionEnableById(long id);

    boolean checkSessionEnable(Session session);
    void disableSessionById(long id);

    void disableAllSessionByUser(User user);

    Session findSessionById(long id);
}
