package com.flb.jobfetcher.infra;

import com.flb.jobfetcher.domain.domain.JobAd;
import com.flb.jobfetcher.domain.domain.JobAdFetcher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobAdWebClient implements JobAdFetcher {
    @Override
    public List<JobAd> fetch() {
        return null;
    }
}
