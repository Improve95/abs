package ru.improve.abs.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageUtil {

    private final MessageSource messageSource;

    public String resolveMessage(String key, String... params) {
        return messageSource.getMessage(key, params, key, LocaleContextHolder.getLocale());
    }
}
