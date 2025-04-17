package ru.improve.abs.info.service.api.controller;

import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.improve.abs.info.service.api.dto.CreditResponse;
import ru.improve.abs.info.service.core.service.CreditService;

@RequiredArgsConstructor
@Controller
public class GraphQlController {

    private final CreditService creditService;

    @QueryMapping(name = "credit")
    public Iterable<CreditResponse> getCredits(DataFetchingEnvironment environment) {
        return creditService.getCredits(environment);
    }

    /*@QueryMapping(name = "payments")
    public Iterable<CreditResponse> getPayments(DataFetchingEnvironment environment) {
        return creditService.getCredits(environment);
    }*/
}
