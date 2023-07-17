package com.flb.jobfetcher.domain.model;

import com.flb.jobfetcher.domain.Aggregation;

public record JobAdStatistics(
    long totalJobAds,
    Aggregation topContractTypes
) {
}
