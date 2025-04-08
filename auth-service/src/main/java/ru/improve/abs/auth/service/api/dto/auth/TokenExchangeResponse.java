package ru.improve.abs.auth.service.api.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class TokenExchangeResponse {

    private String token;
}
