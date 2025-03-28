package ru.improve.abs.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.api.controller.spec.UserControllerSpec;
import ru.improve.abs.api.dto.user.UserResponse;
import ru.improve.abs.core.service.UserService;

import static ru.improve.abs.api.ApiPaths.BECOME;
import static ru.improve.abs.api.ApiPaths.CLIENT;
import static ru.improve.abs.api.ApiPaths.USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(USERS)
public class UserController implements UserControllerSpec {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserResponse> getUserByAuth() {
        UserResponse userResponse = userService.getUserByAuth();
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(BECOME + CLIENT)
    public ResponseEntity<UserResponse> becomeClient() {
        UserResponse userResponse = userService.becomeUserClient();
        return ResponseEntity.ok(userResponse);
    }
}
