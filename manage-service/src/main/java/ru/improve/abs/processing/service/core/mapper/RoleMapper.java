package ru.improve.abs.processing.service.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.abs.processing.service.api.dto.role.RoleResponse;
import ru.improve.abs.processing.service.model.Role;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoleMapper {


    RoleResponse toRoleResponse(Role role);
}
