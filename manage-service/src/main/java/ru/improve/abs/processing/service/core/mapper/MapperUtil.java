package ru.improve.abs.processing.service.core.mapper;

import lombok.experimental.UtilityClass;
import org.mapstruct.Named;
import ru.improve.abs.processing.service.model.Role;
import ru.improve.abs.processing.service.model.User;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.improve.abs.processing.service.core.mapper.MapperUtil.MAPPER_UTIL_NAME;

@Named(MAPPER_UTIL_NAME)
public final class MapperUtil {

    public static final String MAPPER_UTIL_NAME = "MapperUtil";

    public static final String GET_ROLES_ID_FUNC_NAME = "getRolesId";

    @Named(GET_ROLES_ID_FUNC_NAME)
    public static Set<Integer> getRolesId(User user) {
        return user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }
}
