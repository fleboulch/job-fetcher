package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.domain.model.JobAdStorage;

import java.util.Collections;
import java.util.List;

public class JobAdStorageStub implements JobAdStorage {

    private List<JobAd> jobAds;

    @Override
    public void sync(List<JobAd> jobAdsToSave) {
        jobAds = jobAdsToSave;
    }

    @Override
    public long count() {
        return jobAds.size();
    }

    @Override
    public void deleteAll() {
        jobAds = Collections.emptyList();
    }

    public void init(List<JobAd> jobAds) {
        this.jobAds = jobAds;
    }

}
