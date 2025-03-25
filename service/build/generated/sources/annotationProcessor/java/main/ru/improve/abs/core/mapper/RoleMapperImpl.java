package ru.improve.abs.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.improve.abs.api.dto.role.RoleResponse;
import ru.improve.abs.model.Role;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-25T01:03:40+0700",
    comments = "version: 1.6.0, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleResponse toRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.id( role.getId() );
        roleResponse.name( role.getName() );

        return roleResponse.build();
    }
}
