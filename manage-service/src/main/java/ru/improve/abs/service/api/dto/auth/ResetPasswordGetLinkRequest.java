package ru.improve.abs.service.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResetPasswordGetLinkRequest {

    @Schema(defaultValue = "e.davydov@g.nsu.ru")
    private String email;
}
