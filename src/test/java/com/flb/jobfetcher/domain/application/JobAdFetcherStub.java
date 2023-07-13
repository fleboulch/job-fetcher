package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.domain.JobAd;
import com.flb.jobfetcher.domain.domain.JobAdFetcher;

import java.util.List;

public class JobAdFetcherStub implements JobAdFetcher {

    private List<JobAd> jobAds;

    @Override
    public List<JobAd> fetch() {
        return jobAds;
    }

    public void init(List<JobAd> jobAds) {
        this.jobAds = jobAds;
    }
}
