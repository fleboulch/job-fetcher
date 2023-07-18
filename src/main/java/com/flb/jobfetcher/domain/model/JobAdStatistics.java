package com.flb.jobfetcher.domain.model;

public record JobAdStatistics(
    long totalJobAds,
    Aggregation topContractTypes,
    Aggregation top10Cities
) {
}
