package ru.improve.abs.auth.service.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.improve.abs.auth.service.core.service.UserService;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) {
        return userService.findUserByEmail(login);
    }
}
