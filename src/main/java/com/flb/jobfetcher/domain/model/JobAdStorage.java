package com.flb.jobfetcher.domain.model;

import java.util.List;

public interface JobAdStorage {

    void sync(List<JobAd> jobAds);

    long count();

    void deleteAll();
}
