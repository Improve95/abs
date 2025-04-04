package ru.improve.abs.auth.service.core.service;


import ru.improve.abs.auth.service.api.dto.user.UserResponse;
import ru.improve.abs.auth.service.model.User;

public interface UserService {

    UserResponse becomeUserClient();

    UserResponse addRole(int userId, int roleId);

    UserResponse removeRole(int userId, int roleId);

    UserResponse getUserById(int id);

    UserResponse getUserByAuth();

    User getUserFromAuthentication();

    User findUserById(int id);

    User findUserByEmail(String email);
}
