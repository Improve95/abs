package ru.improve.abs.service.util;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import ru.improve.abs.service.api.dto.graphql.BetweenFilter;
import ru.improve.abs.service.api.dto.graphql.FilterType;
import ru.improve.abs.service.api.exception.ServiceException;

import java.lang.reflect.Field;
import java.util.Collection;

import static ru.improve.abs.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@SuppressWarnings("unchecked")
public final class GraphQlUtil {

    public static final String PAGE_ARGUMENT = "page";

    public static final String FILTER_ARGUMENT = "filter";

    public static final String PAYMENT_DATA_LOADER = "PAYMENT_DATA_LOADER";

    public final static String LOWER_THEN_OR_EQUAL_FIELD_NAME = "lte";

    public final static String GREATER_THEN_OR_EQUAL_FIELD_NAME = "gte";

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

    private static <T, Y> Specification<T> chooseFieldTypeAndCreateSpec(
            Specification<T> spec,
            Field field,
            Y fieldValue
    ) {
        FilterType filterType = field.getAnnotation(FilterType.class);
        if (filterType != null) {
            String fieldName = filterType.fieldName();
            if (fieldName.isEmpty()) {
                fieldName = field.getName();
            }
            switch (filterType.type()) {
                case EQUALS -> {
                    return createEqualsFilterSpec(spec, fieldName, fieldValue);
                }
                case BETWEEN -> {
                    if (fieldValue instanceof BetweenFilter betweenFilter) {
                        return createBetweenFilterSpec(spec, betweenFilter, fieldName);
                    }
                }
                case CONTAINS -> {
                    if (fieldValue instanceof Collection<?> collection) {
                        return addContainsSpec(spec, filterType.referencedColumnName(), collection);
                    }
                }
                case JOIN_ENTITY_ID_CONTAINS -> {
                    if (fieldValue instanceof Collection<?> collection) {
                        return addJoinEntityByIdSpec(
                                spec,
                                filterType.mappedBy(),
                                filterType.referencedColumnName(),
                                collection
                        );
                    }
                }
            }
        }
        return spec;
    }

    private static <T> Specification<T> createEqualsFilterSpec(
            Specification<T> spec,
            String fieldName,
            Object fieldValue
    ) {
        return spec.and(addEqualsSpec(fieldName, fieldValue));
    }

    private static <T> Specification<T> createBetweenFilterSpec(
            Specification<T> spec,
            BetweenFilter<?> betweenFilter,
            String fieldName
    ) {
        try {
            for (Field field : betweenFilter.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldValue = field.get(betweenFilter);
                if (fieldValue != null) {
                    Class<?> comparableClass = betweenFilter.getComparableClass();
                    Comparable<?> castedValue = (Comparable<?>) comparableClass.cast(fieldValue);
                    switch (field.getName()) {
                        case LOWER_THEN_OR_EQUAL_FIELD_NAME -> spec = spec.and(
                                addLowerThanOrEqualsSpec(
                                        fieldName,
                                        (Comparable<Object>) castedValue
                                )
                        );
                        case GREATER_THEN_OR_EQUAL_FIELD_NAME -> spec = spec.and(
                                addGraterThanOrEqualsSpec(
                                        fieldName,
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
            String mappedBy,
            String referencedColumnName,
            Collection<?> ids)
    {
        return spec.and(
                ((root, query, criteriaBuilder) -> {
                    Join<Object, Object> credit = root.join(mappedBy);
                    return criteriaBuilder.in(credit.get(referencedColumnName)).value(ids);
                })
        );
    }

    private static <T> Specification<T> addContainsSpec(Specification<T> spec,
                                                        String referencedColumnName,
                                                        Collection<?> items)
    {
        return spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get(referencedColumnName)).value(items));
    }
}
