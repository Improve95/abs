package ru.improve.abs.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.improve.abs.api.dto.user.SignInRequest;
import ru.improve.abs.api.dto.user.SignInResponse;
import ru.improve.abs.api.dto.user.UserResponse;
import ru.improve.abs.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-25T01:03:40+0700",
    comments = "version: 1.6.0, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(SignInRequest signInRequest) {
        if ( signInRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( signInRequest.getEmail() );
        user.password( signInRequest.getPassword() );
        user.name( signInRequest.getName() );
        user.employment( signInRequest.getEmployment() );

        return user.build();
    }

    @Override
    public SignInResponse toSignInUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        SignInResponse.SignInResponseBuilder signInResponse = SignInResponse.builder();

        signInResponse.id( user.getId() );

        return signInResponse.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.rolesId( MapperUtil.getRolesId( user ) );
        userResponse.id( user.getId() );
        userResponse.email( user.getEmail() );
        userResponse.name( user.getName() );
        userResponse.employment( user.getEmployment() );

        return userResponse.build();
    }
}
