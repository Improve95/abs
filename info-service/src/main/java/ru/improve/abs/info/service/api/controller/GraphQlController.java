package ru.improve.abs.info.service.api.controller;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.abs.info.service.api.dto.CreditResponse;
import ru.improve.abs.info.service.core.service.CreditService;

import java.util.List;

@GraphQLApi
@RequiredArgsConstructor
@Service
public class GraphQlController {

    private final CreditService creditService;

//    @QueryMapping(name = "credit")
    @GraphQLQuery
    public List<CreditResponse> credits(
            @GraphQLArgument @GraphQLNonNull int pageNumber,
            @GraphQLArgument @GraphQLNonNull int pageSize
    ) {
        return List.of(
                CreditResponse.builder()
                        .id(1)
                        .build()
        );
//        return creditService.getCredits(null);
    }
}
