package ru.improve.abs.info.service.uitl;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationAccumulator<T> {

    public Specification<T> and(Specification<T> specification, Specification<T> specification2) {
        return specification.and(specification2);
    }
}
