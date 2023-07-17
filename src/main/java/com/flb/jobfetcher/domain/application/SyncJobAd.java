package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.Aggregation;
import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.domain.model.JobAdFetcher;
import com.flb.jobfetcher.domain.model.JobAdStatistics;
import com.flb.jobfetcher.domain.model.JobAdStorage;
import org.springframework.data.util.Pair;
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

    public JobAdStatistics handle() {
        Pair<List<JobAd>, Aggregation> fetchedJobAds = fetcher.fetch();
        storage.sync(fetchedJobAds.getFirst());
        List<JobAd> jobAds = storage.findAll();
        return new JobAdStatistics(jobAds.size(), fetchedJobAds.getSecond());
    }
}
