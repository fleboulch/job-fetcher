package com.flb.jobfetcher.infra.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
class AggregationFilterDto {
    private String valeurPossible;
    private long nbResultats;
}
