package ru.improve.abs.processing.service.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.abs.processing.service.api.dto.user.SignInRequest;
import ru.improve.abs.processing.service.api.dto.user.SignInResponse;
import ru.improve.abs.processing.service.api.dto.user.UserResponse;
import ru.improve.abs.processing.service.model.User;

import static ru.improve.abs.processing.service.core.mapper.MapperUtil.GET_ROLES_ID_FUNC_NAME;
import static ru.improve.abs.processing.service.core.mapper.MapperUtil.MAPPER_UTIL_NAME;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = MapperUtil.class
)
public interface UserMapper {

    User toUser(SignInRequest signInRequest);

    SignInResponse toSignInUserResponse(User user);

    @Mapping(
            target = "rolesId",
            qualifiedByName = { MAPPER_UTIL_NAME, GET_ROLES_ID_FUNC_NAME },
            source = "user"
    )
    UserResponse toUserResponse(User user);
}
