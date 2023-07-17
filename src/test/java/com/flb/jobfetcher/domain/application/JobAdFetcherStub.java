package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.Aggregation;
import com.flb.jobfetcher.domain.AggregationDetails;
import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.domain.model.JobAdFetcher;
import org.springframework.data.util.Pair;

import java.util.List;

public class JobAdFetcherStub implements JobAdFetcher {

    private List<JobAd> jobAds;

    @Override
    public Pair<List<JobAd>, Aggregation> fetch() {
        return Pair.of(jobAds,
            new Aggregation(List.of(
            new AggregationDetails("CDI", 10),
            new AggregationDetails("MIS",5),
            new AggregationDetails("DDI", 2)
        )));
    }

    public void init(List<JobAd> jobAds) {
        this.jobAds = jobAds;
    }
}
