package ru.improve.abs.processing.service.core.service;


import ru.improve.abs.processing.service.model.Session;
import ru.improve.abs.processing.service.model.User;

public interface SessionService {

    Session create(User user);

    boolean checkSessionEnableById(long id);

    boolean checkSessionEnable(Session session);

    void setUserSessionDisable(User user);
}
