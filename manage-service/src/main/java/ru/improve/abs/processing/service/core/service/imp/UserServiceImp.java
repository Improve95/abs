package ru.improve.abs.processing.service.core.service.imp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.improve.abs.processing.service.api.dto.user.UserResponse;
import ru.improve.abs.processing.service.api.exception.ServiceException;
import ru.improve.abs.processing.service.core.mapper.UserMapper;
import ru.improve.abs.processing.service.core.repository.UserRepository;
import ru.improve.abs.processing.service.core.service.RoleService;
import ru.improve.abs.processing.service.core.service.UserService;
import ru.improve.abs.processing.service.model.Role;
import ru.improve.abs.processing.service.model.User;

import static ru.improve.abs.processing.service.api.exception.ErrorCode.NOT_FOUND;
import static ru.improve.abs.processing.service.util.SecurityUtil.CLIENT_ROLE;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse getUserById(int id) {
        User user = findUserById(id);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse getUserByAuth() {
        User user = getUserFromAuthentication();
        return userMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse becomeUserClient() {
        User user = getUserFromAuthentication();
        Role clientRole = roleService.findRoleByName(CLIENT_ROLE);
        if (user.getRoles().contains(clientRole)) {
            return userMapper.toUserResponse(user);
        }
        user.getRoles().add(clientRole);

        return userMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse addRole(int userId, int roleId) {
        User user = findUserById(userId);
        Role role = roleService.findRoleById(roleId);
        user.getRoles().add(role);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse removeRole(int userId, int roleId) {
        User user = findUserById(userId);
        user.getRoles().removeIf(role -> role.getId() == roleId);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public User getUserFromAuthentication() {
        User detachUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return detachUser;
//        int userId = detachUser.getId();
//        return findUserById(userId);
    }

    @Transactional
    @Override
    public User findUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "user", "id"));
    }

    @Transactional
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "user", "email"));
    }
}
