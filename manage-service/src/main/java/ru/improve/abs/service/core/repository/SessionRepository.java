package ru.improve.abs.service.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.abs.service.model.Session;
import ru.improve.abs.service.model.User;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findAllByUserAndIsEnable(User user, boolean enable);
}
