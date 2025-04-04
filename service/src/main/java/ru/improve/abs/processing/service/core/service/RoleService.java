package ru.improve.abs.processing.service.core.service;

import ru.improve.abs.processing.service.api.dto.role.RoleResponse;
import ru.improve.abs.processing.service.model.Role;

import java.util.List;

public interface RoleService {

    List<RoleResponse> getAllRoles();

    Role findRoleById(int id);

    Role findRoleByName(String name);
}
