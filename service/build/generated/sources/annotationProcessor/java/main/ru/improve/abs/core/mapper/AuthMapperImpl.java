package ru.improve.abs.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.improve.abs.api.dto.auth.LoginResponse;
import ru.improve.abs.model.Session;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-25T01:03:40+0700",
    comments = "version: 1.6.0, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public LoginResponse toLoginResponse(Session session) {
        if ( session == null ) {
            return null;
        }

        LoginResponse.LoginResponseBuilder loginResponse = LoginResponse.builder();

        loginResponse.id( session.getId() );
        loginResponse.issuedAt( session.getIssuedAt() );
        loginResponse.expiredAt( session.getExpiredAt() );

        return loginResponse.build();
    }
}
