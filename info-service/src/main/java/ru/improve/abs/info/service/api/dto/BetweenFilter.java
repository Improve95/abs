package ru.improve.abs.info.service.api.dto;

public interface BetweenFilter<T> {

    Comparable<T> getGte();

    Comparable<T> getLte();

    Class<T> getComparableClass();
}
