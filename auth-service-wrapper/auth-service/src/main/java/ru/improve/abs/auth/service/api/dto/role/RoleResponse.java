package ru.improve.abs.auth.service.api.dto.role;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class RoleResponse {

    private int id;

    private String name;
}
