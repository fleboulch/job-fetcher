package com.flb.jobfetcher.infra.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
class FilterDto {
    private String filtre;
    private List<AggregationFilterDto> agregation;
}
