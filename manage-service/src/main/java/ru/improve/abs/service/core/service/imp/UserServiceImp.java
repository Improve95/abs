package ru.improve.abs.service.core.service.imp;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.improve.abs.service.api.dto.user.UserResponse;
import ru.improve.abs.service.api.exception.ServiceException;
import ru.improve.abs.service.core.mapper.UserMapper;
import ru.improve.abs.service.core.repository.UserRepository;
import ru.improve.abs.service.core.service.RoleService;
import ru.improve.abs.service.core.service.UserService;
import ru.improve.abs.service.model.Role;
import ru.improve.abs.service.model.User;
import ru.improve.abs.service.util.SecurityUtil;

import static ru.improve.abs.service.api.exception.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final UserMapper userMapper;

    private final EntityManager em;

    @Transactional
    @Override
    public UserResponse getUserById(int id) {
        User user = findUserById(id);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse getRefreshUserByAuth() {
        User user = getUserFromAuthentication();
        em.merge(user);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse becomeUserClient() {
        User user = getUserFromAuthentication();
        Role clientRole = roleService.findRoleByName(SecurityUtil.CLIENT_ROLE);
        user.getRoles().add(clientRole);
        userRepository.save(user);
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

    /**
     * BE CAREFULLY - THIS METHOD RETURNING DETACH USER
     * <p>YOU SHOULD UPDATE USER IF YOU NEED ACTUAL DATA</p>
     **/
    @Override
    public User getUserFromAuthentication() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
