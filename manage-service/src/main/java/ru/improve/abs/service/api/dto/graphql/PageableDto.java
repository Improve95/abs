package ru.improve.abs.service.api.dto.graphql;

import lombok.Data;

@Data
public class PageableDto {

    private int pageNumber;

    private int pageSize;
}
