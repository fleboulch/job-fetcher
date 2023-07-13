package com.flb.jobfetcher.infra.storage;

import com.flb.jobfetcher.domain.domain.JobAd;
import com.flb.jobfetcher.domain.domain.JobAdStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobAdRepository implements JobAdStorage {

    @Override
    public void sync(List<JobAd> jobAds) {

    }

    @Override
    public List<JobAd> findAll() {
        return null;
    }
}
