package ru.improve.abs.info.service.core.service.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.improve.abs.info.service.model.credit.Credit;

public final class CreditSpec {

    public static Specification<Credit> hasEqualField(String fieldName, Object equalsObj) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(fieldName), equalsObj);
    }
}
