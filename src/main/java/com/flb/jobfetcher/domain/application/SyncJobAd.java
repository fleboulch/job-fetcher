package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.domain.JobAd;
import com.flb.jobfetcher.domain.domain.JobAdFetcher;
import com.flb.jobfetcher.domain.domain.JobAdStatistics;
import com.flb.jobfetcher.domain.domain.JobAdStorage;
import com.flb.jobfetcher.domain.domain.SyncMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncJobAd {

    private final JobAdFetcher fetcher;
    private final JobAdStorage storage;

    public SyncJobAd(JobAdFetcher fetcher, JobAdStorage storage) {
        this.fetcher = fetcher;
        this.storage = storage;
    }

    public JobAdStatistics handle(SyncMode mode) {
        List<JobAd> fetchedJobAds = fetcher.fetch();
        storage.sync(fetchedJobAds);
        List<JobAd> jobAds = storage.findAll();
        return new JobAdStatistics(jobAds.size());
    }
}
