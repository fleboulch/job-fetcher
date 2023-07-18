package com.flb.jobfetcher.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class JobAdStatisticsResponse {

    private long total;
    private List<AggregationDto> topContractTypes;
    private List<AggregationDto> topCities;
}
