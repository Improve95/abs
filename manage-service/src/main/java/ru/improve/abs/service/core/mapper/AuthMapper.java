package ru.improve.abs.service.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.abs.service.api.dto.auth.login.LoginResponse;
import ru.improve.abs.service.model.Session;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuthMapper {

    LoginResponse toLoginResponse(Session session);
}
