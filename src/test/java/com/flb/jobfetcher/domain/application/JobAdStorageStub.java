package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.domain.model.JobAdStorage;

import java.util.List;
import java.util.Objects;

public class JobAdStorageStub implements JobAdStorage {

    private boolean syncWasCalled = false;
    private List<JobAd> jobAds;

    @Override
    public void sync(List<JobAd> jobAdsToSave) {
        if (Objects.equals(jobAds, jobAdsToSave)) {
            syncWasCalled = true;
        }
    }

    @Override
    public long count() {
        return jobAds.size();
    }

    public void init(List<JobAd> jobAds) {
        this.jobAds = jobAds;
    }

    public boolean isSyncWasCalled() {
        return syncWasCalled;
    }

}
