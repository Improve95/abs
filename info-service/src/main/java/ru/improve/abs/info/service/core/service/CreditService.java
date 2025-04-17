package ru.improve.abs.info.service.core.service;

import graphql.schema.DataFetchingEnvironment;
import ru.improve.abs.info.service.api.dto.CreditResponse;

import java.util.List;

public interface CreditService {

    List<CreditResponse> getCredits(DataFetchingEnvironment environment);
}
