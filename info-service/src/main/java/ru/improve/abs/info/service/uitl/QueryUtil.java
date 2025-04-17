package ru.improve.abs.info.service.uitl;

import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public final class QueryUtil {

    public final static String PAGE_NUMBER = "pageNumber";

    public final static String PAGE_SIZE = "pageSize";

    public final static String ID = "id";

    public final static String INITIAL_AMOUNT = "";

    public final static String TAKING_DATE = "takingDate";

    public final static String PERCENT = "percent";

    public final static String CREDIT_PERIOD = "creditPeriod";

    public final static String CREDIT_STATUS = "creditStatus";

    public final static String USER_ID = "userId";

    public final static String CREDIT_TARIFF = "creditTariff";

    public static <T> Specification<T> addEqualsSpec(Map.Entry<String, Object> entry) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue());
    }
}
