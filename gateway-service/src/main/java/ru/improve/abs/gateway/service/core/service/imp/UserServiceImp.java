package ru.improve.abs.gateway.service.core.service.imp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.abs.gateway.service.api.exception.ServiceException;
import ru.improve.abs.gateway.service.core.repository.UserRepository;
import ru.improve.abs.gateway.service.core.service.UserService;
import ru.improve.abs.gateway.service.model.User;

import static ru.improve.abs.gateway.service.api.exception.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "user", "email"));
    }
}
