package com.flb.jobfetcher.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class AggregationDto {

    private String name;
    private long occurrences;
}
