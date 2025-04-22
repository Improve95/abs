package ru.improve.abs.info.service.uitl;

import org.springframework.data.jpa.domain.Specification;
import ru.improve.abs.info.service.api.dto.DriftingFilter;
import ru.improve.abs.info.service.api.exception.ServiceException;

import java.lang.reflect.Field;

import static ru.improve.abs.info.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

public final class QueryUtil {

    /*public final static String PAGE_NUMBER = "pageNumber";

    public final static String PAGE_SIZE = "pageSize";

    public final static String ID = "id";

    public final static String INITIAL_AMOUNT = "";

    public final static String TAKING_DATE = "takingDate";

    public final static String PERCENT = "percent";

    public final static String CREDIT_PERIOD = "creditPeriod";

    public final static String CREDIT_STATUS = "creditStatus";

    public final static String USER_ID = "userId";

    public final static String CREDIT_TARIFF = "creditTariff";*/

    public final static String LOWER_THEN_OR_EQUAL_FIELD_NAME = "lte";

    public final static String GREATER_THEN_OR_EQUAL_FIELD_NAME = "gte";

    public static <T> Specification<T> createCreditSpecFromArguments (
            Specification<T> specification,
            Object entityFilter
    ) {
        if (entityFilter == null) {
            return specification;
        }

        try {
            for (Field field : entityFilter.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldValue = field.get(entityFilter);
                if (fieldValue != null) {
                    if (fieldValue instanceof DriftingFilter driftingFilter) {
                        specification = createDriftingFilterSpec(specification, driftingFilter);
                    } else {
                        specification = createEntityFilterSpec(specification, field.getName(), fieldValue);
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }
        return specification;
    }

    private static <T> Specification<T> createEntityFilterSpec(
            Specification<T> specification,
            String fieldName,
            Object fieldValue
    ) {
        return specification.and(addEqualsSpec(fieldName, fieldValue));
    }
    
    private static <T> Specification<T> createDriftingFilterSpec(
            Specification<T> specification,
            DriftingFilter driftingFilter
    ) {
        try {
            for (Field field : driftingFilter.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldValue = field.get(driftingFilter);
                if (fieldValue != null) {
                    Class<?> comparableClass = driftingFilter.getComparableClass();
                    Comparable<?> castedValue = (Comparable<?>) comparableClass.cast(fieldValue);
                    switch (field.getName()) {
                        case LOWER_THEN_OR_EQUAL_FIELD_NAME -> specification = specification.and(
                                addLowerThanOrEqualsSpec(
                                        driftingFilter.getEntityFieldName(),
                                        (Comparable<Object>) castedValue
                                )
                        );
                        case GREATER_THEN_OR_EQUAL_FIELD_NAME -> specification = specification.and(
                                addGraterThanOrEqualsSpec(
                                        field.getName(),
                                        (Comparable<Object>) castedValue
                                )
                        );
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return specification;
    }

    private static <T> Specification<T> addEqualsSpec(String fieldName, Object target) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(fieldName), target);
    }

    private static <T, Y extends Comparable<? super Y>> Specification<T> addLowerThanOrEqualsSpec(
            String fieldName, Y target
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), target);
    }

    private static <T, Y extends Comparable<? super Y>> Specification<T> addGraterThanOrEqualsSpec(
            String fieldName, Y target
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), target);
    }
}
