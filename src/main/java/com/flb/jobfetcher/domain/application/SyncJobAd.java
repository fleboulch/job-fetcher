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
        storage.deleteAll(); // I'm cheating a bit here but it fits the need for full and incremental sync. It's not optimal
        storage.sync(fetchedJobAds.getFirst());
        return new JobAdStatistics(storage.count(), fetchedJobAds.getSecond());
    }
}
