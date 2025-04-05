package ru.improve.abs.gateway.service.core.service;

import ru.improve.abs.gateway.service.model.User;

public interface UserService {

    User findUserByEmail(String email);
}
