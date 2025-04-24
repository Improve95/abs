package ru.improve.abs.info.service.uitl;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import ru.improve.abs.info.service.api.dto.DriftingFilter;
import ru.improve.abs.info.service.api.dto.FilterType;
import ru.improve.abs.info.service.api.exception.ServiceException;

import java.lang.reflect.Field;
import java.util.Collection;

import static ru.improve.abs.info.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

public final class GraphQlUtil {

    public static final String PAGE_ARGUMENT = "page";

    public static final String FILTER_ARGUMENT = "filter";

    public static final String PAYMENT_DATA_LOADER = "PAYMENT_DATA_LOADER";

    public final static String LOWER_THEN_OR_EQUAL_FIELD_NAME = "lte";

    public final static String GREATER_THEN_OR_EQUAL_FIELD_NAME = "gte";

    public final static String JOIN_KEY_FIELD_NAME = "id";

    public static <T> Specification<T> createSpecFromArguments(
            Specification<T> spec,
            Object entityFilter
    ) {
        if (entityFilter == null) {
            return spec;
        }

        try {
            for (Field field : entityFilter.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldValue = field.get(entityFilter);
                if (fieldValue != null) {
                    spec = chooseFieldTypeAndCreateSpec(spec, field, fieldValue);
                }
            }
        } catch (IllegalAccessException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }
        return spec;
    }

    private static <T, Y> Specification<T> chooseFieldTypeAndCreateSpec(Specification<T> spec, Field field, Y fieldValue) {
        FilterType filterType = field.getAnnotation(FilterType.class);
        if (filterType != null) {
            switch (filterType.type()) {
                case EQUALS -> {
                    return createEntityFilterSpec(spec, field.getName(), fieldValue);
                }
                case BETWEEN -> {
                    if (fieldValue instanceof DriftingFilter driftingFilter) {
                        return createDriftingFilterSpec(spec, driftingFilter);
                    }
                }
                case CONTAINS -> {
                    
                }
                case JOIN_ENTITY_ID_CONTAINS -> {
                    if (fieldValue instanceof Collection<?> collection) {
                        return addJoinEntityByIdSpec(spec, field.getName(), collection);
                    }
                }
            }
        }
        return spec;
    }

    private static <T> Specification<T> createEntityFilterSpec(
            Specification<T> spec,
            String fieldName,
            Object fieldValue
    ) {
        return spec.and(addEqualsSpec(fieldName, fieldValue));
    }

    private static <T> Specification<T> createDriftingFilterSpec(
            Specification<T> spec,
            DriftingFilter<?> driftingFilter
    ) {
        try {
            for (Field field : driftingFilter.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldValue = field.get(driftingFilter);
                if (fieldValue != null) {
                    Class<?> comparableClass = driftingFilter.getComparableClass();
                    Comparable<?> castedValue = (Comparable<?>) comparableClass.cast(fieldValue);
                    switch (field.getName()) {
                        case LOWER_THEN_OR_EQUAL_FIELD_NAME -> spec = spec.and(
                                addLowerThanOrEqualsSpec(
                                        driftingFilter.getEntityFieldName(),
                                        (Comparable<Object>) castedValue
                                )
                        );
                        case GREATER_THEN_OR_EQUAL_FIELD_NAME -> spec = spec.and(
                                addGraterThanOrEqualsSpec(
                                        driftingFilter.getEntityFieldName(),
                                        (Comparable<Object>) castedValue
                                )
                        );
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return spec;
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

    private static <T> Specification<T> addJoinEntityByIdSpec(
            Specification<T> spec,
            String fieldName,
            Collection<?> ids)
    {
        String entityName = fieldName.substring(0, fieldName.length() - 3);
        return spec.and(
                ((root, query, criteriaBuilder) -> {
                    Join<Object, Object> credit = root.join(entityName);
                    return criteriaBuilder.in(credit.get(JOIN_KEY_FIELD_NAME)).value(ids);
                })
        );
    }
}
