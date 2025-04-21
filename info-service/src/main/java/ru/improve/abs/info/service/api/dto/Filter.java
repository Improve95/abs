package ru.improve.abs.info.service.api.dto;

public interface Filter<T> {

    Comparable<T> getGt();

    Comparable<T> getIn();

    Comparable<T> getLt();
}
