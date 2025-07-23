package ru.improve.abs.service.api.dto.auth.resetPassword;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ResetPasswordSendPasswordRequest {

    @NotNull
    @Size(min = 8)
    private String password;
}
