package ru.improve.abs.gateway.service.core.service;

import ru.improve.abs.gateway.service.model.Session;
import ru.improve.abs.gateway.service.model.User;

public interface SessionService {

    Session create(User user);

    boolean checkSessionEnableById(long id);

    boolean checkSessionEnable(Session session);

    void setUserSessionDisable(User user);
}
