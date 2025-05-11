package ru.improve.abs.service.api.dto.graphql;

public interface BetweenFilter<T> {

    Comparable<T> getGte();

    Comparable<T> getLte();

    Class<T> getComparableClass();
}
