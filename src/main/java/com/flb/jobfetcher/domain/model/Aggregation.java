package com.flb.jobfetcher.domain.model;

import java.util.List;

public record Aggregation(
    List<AggregationDetails> values
) {
}
