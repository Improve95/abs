package ru.improve.abs.service.api.dto.auth.signin;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SignInResponse {

    private int id;

    private String accessToken;

    private String refreshToken;
}
