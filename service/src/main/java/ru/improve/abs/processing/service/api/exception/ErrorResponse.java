package ru.improve.abs.processing.service.api.exception;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ErrorResponse {

    private int errorCode;

    private String message;
}
