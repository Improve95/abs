package ru.improve.abs.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.abs.service.model.PasswordResetRequest;

import java.util.Optional;

public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, Long> {

    Optional<PasswordResetRequest> findByToken(String token);
}
