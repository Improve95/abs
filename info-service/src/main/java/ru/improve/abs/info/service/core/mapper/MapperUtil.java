package ru.improve.abs.info.service.core.mapper;

import org.mapstruct.Named;
import ru.improve.abs.info.service.model.credit.Credit;

import static ru.improve.abs.info.service.core.mapper.MapperUtil.MAPPER_UTIL_NAME;

@Named(MAPPER_UTIL_NAME)
public final class MapperUtil {

    public static final String MAPPER_UTIL_NAME = "MAPPER_UTIL";

    public static final String GET_CREDIT_ID_FUNC_NAME = "GET_CREDIT_ID_FUNC_NAME";

    @Named(GET_CREDIT_ID_FUNC_NAME)
    public static Long getCreditId(Credit credit) {
        return credit.getId();
    }
}
