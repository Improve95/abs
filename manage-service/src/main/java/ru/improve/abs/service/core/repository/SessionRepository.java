package ru.improve.abs.service.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.abs.service.model.Session;
import ru.improve.abs.service.model.User;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByUserAndIsEnable(User user, boolean isEnable);
}
