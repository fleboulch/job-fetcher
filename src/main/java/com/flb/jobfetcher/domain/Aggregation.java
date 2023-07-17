package com.flb.jobfetcher.domain;

import java.util.List;

public record Aggregation(
    List<AggregationDetails> values
) {
}
