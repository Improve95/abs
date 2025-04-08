package ru.improve.abs.gateway.service.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

import java.util.List;

public final class TokenUtil {

    public static String extractToken(HttpHeaders headers) {
        List<String> authHeaders = headers.get(HttpHeaders.AUTHORIZATION);
        if (authHeaders != null && !authHeaders.isEmpty()) {
            return authHeaders.getFirst().replace("Bearer ", "");
        }
        return null;
    }
}
