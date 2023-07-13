package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.domain.JobAd;
import com.flb.jobfetcher.domain.domain.JobAdFetcher;
import com.flb.jobfetcher.domain.domain.JobAdStatistics;
import com.flb.jobfetcher.domain.domain.SyncMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncJobAd {

    private final JobAdFetcher fetcher;

    public SyncJobAd(JobAdFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public JobAdStatistics handle(SyncMode mode) {
        List<JobAd> jobAds = fetcher.fetch();
        return new JobAdStatistics(jobAds.size());
    }
}
