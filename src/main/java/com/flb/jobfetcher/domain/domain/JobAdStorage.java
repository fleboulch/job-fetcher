package com.flb.jobfetcher.domain.domain;

import java.util.List;

public interface JobAdStorage {

    void sync(List<JobAd> jobAds);

    List<JobAd> findAll();
}
