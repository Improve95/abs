package ru.improve.abs.info.service.api.dto;

import io.leangen.graphql.annotations.GraphQLNonNull;
import lombok.Data;

@Data
public class PageableDto {

    @GraphQLNonNull
    private int pageNumber;

    @GraphQLNonNull
    private int pageSize;
}
