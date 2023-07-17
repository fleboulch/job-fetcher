package com.flb.jobfetcher.domain.model;

import com.flb.jobfetcher.domain.Aggregation;
import org.springframework.data.util.Pair;

import java.util.List;

public interface JobAdFetcher {

    Pair<List<JobAd>, Aggregation> fetch();
}
