package ru.improve.abs.service.core.mapper;

import org.mapstruct.Named;
import ru.improve.abs.service.api.dto.credit.CreditTariffResponse;
import ru.improve.abs.service.model.CreditTariff;
import ru.improve.abs.service.model.Role;
import ru.improve.abs.service.model.User;
import ru.improve.abs.service.model.credit.Credit;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.improve.abs.service.core.mapper.MapperUtil.MAPPER_UTIL_NAME;

@Named(MAPPER_UTIL_NAME)
public final class MapperUtil {

    public static final String MAPPER_UTIL_NAME = "MapperUtil";

    public static final String GET_ROLES_ID_FUNC_NAME = "getRolesId";

    public static final String GET_CREDIT_TARIFF_RESPONSE_FUNC_NAME = "getCreditTariffResponse";

    @Named(GET_ROLES_ID_FUNC_NAME)
    public static Set<Integer> getRolesId(User user) {
        return user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }

    @Named(GET_CREDIT_TARIFF_RESPONSE_FUNC_NAME)
    public static CreditTariffResponse getCreditTariffResponse(Credit credit) {
        CreditTariff creditTariff = credit.getCreditTariff();
        return CreditTariffResponse.builder()
                .id(creditTariff.getId())
                .type(creditTariff.getType())
                .upToAmount(creditTariff.getUpToAmount())
                .upToCreditDuration(creditTariff.getUpToCreditDuration())
                .creditPercent(credit.getPercent())
                .build();
    }
}
