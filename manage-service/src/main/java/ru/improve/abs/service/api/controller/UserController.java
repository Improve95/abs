package ru.improve.abs.service.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.service.api.controller.spec.UserControllerSpec;
import ru.improve.abs.service.api.dto.user.UserResponse;
import ru.improve.abs.service.core.service.UserService;

import static ru.improve.abs.service.api.ApiPaths.BECOME;
import static ru.improve.abs.service.api.ApiPaths.CLIENT;
import static ru.improve.abs.service.api.ApiPaths.USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(USERS)
public class UserController implements UserControllerSpec {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserResponse> getUserByAuth() {
        UserResponse userResponse = userService.getRefreshUserByAuth();
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(BECOME + CLIENT)
    public ResponseEntity<UserResponse> becomeClient() {
        UserResponse userResponse = userService.becomeUserClient();
        return ResponseEntity.ok(userResponse);
    }
}
