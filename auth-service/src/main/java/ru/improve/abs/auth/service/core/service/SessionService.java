package ru.improve.abs.auth.service.core.service;


import ru.improve.abs.auth.service.model.Session;
import ru.improve.abs.auth.service.model.User;

public interface SessionService {

    Session create(User user);

    boolean checkSessionEnableById(long id);

    boolean checkSessionEnable(Session session);

    void setUserSessionDisable(User user);
}
