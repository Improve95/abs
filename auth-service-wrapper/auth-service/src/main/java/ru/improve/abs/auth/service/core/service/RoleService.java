package ru.improve.abs.auth.service.core.service;

import ru.improve.abs.auth.service.api.dto.role.RoleResponse;
import ru.improve.abs.auth.service.model.Role;

import java.util.List;

public interface RoleService {

    List<RoleResponse> getAllRoles();

    Role findRoleById(int id);

    Role findRoleByName(String name);
}
