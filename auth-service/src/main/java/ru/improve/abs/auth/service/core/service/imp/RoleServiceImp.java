package ru.improve.abs.auth.service.core.service.imp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.abs.auth.service.api.dto.role.RoleResponse;
import ru.improve.abs.auth.service.api.exception.ServiceException;
import ru.improve.abs.auth.service.core.mapper.RoleMapper;
import ru.improve.abs.auth.service.core.repository.RoleRepository;
import ru.improve.abs.auth.service.core.service.RoleService;
import ru.improve.abs.auth.service.model.Role;

import java.util.List;

import static ru.improve.abs.auth.service.api.exception.ErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Transactional
    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Transactional
    @Override
    public Role findRoleById(int id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "role", "id"));
    }

    @Transactional
    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ServiceException(NOT_FOUND, "role", "email"));
    }
}
