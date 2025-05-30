package ru.improve.abs.auth.service.api.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SignInResponse {

    private int id;
}
