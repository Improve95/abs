package ru.improve.abs.auth.service.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.auth.service.api.controller.spec.RoleControllerSpec;
import ru.improve.abs.auth.service.api.dto.role.RoleResponse;
import ru.improve.abs.auth.service.core.service.RoleService;

import java.util.List;

import static ru.improve.abs.auth.service.api.ApiPaths.ROLES;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROLES)
public class RoleController implements RoleControllerSpec {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getRoleList() {
        List<RoleResponse> roleResponses = roleService.getAllRoles();
        return ResponseEntity.ok(roleResponses);
    }
}
