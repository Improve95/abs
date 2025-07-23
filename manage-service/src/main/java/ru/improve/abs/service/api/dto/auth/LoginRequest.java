package ru.improve.abs.service.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @Schema(example = "gmail1@gmail.com")
    @NotNull
    @NotBlank
    private String login;

    @Schema(example = "password1")
    @NotNull
    @NotBlank
    private String password;
}
