package ru.improve.abs.service.core.service;

import ru.improve.abs.service.api.dto.role.RoleResponse;
import ru.improve.abs.service.model.Role;

import java.util.List;

public interface RoleService {

    List<RoleResponse> getAllRoles();

    Role findRoleById(int id);

    Role findRoleByName(String name);
}
