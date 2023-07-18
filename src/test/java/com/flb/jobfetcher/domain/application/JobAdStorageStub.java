package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.model.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JobAdStorageStub implements JobAdStorage {

    private List<JobAd> jobAds;

    @Override
    public void sync(List<JobAd> jobAdsToSave) {
        jobAds = jobAdsToSave;
    }

    @Override
    public void deleteAll() {
        jobAds = Collections.emptyList();
    }

    @Override
    public JobAdStatistics computeStatistics() {
        return new JobAdStatistics(
            jobAds.size(),
            top10ContractTypes(),
            top10Cities());
    }

    private Aggregation top10ContractTypes() {
        return top10(JobAd::getContractType);
    }

    private Aggregation top10Cities() {
        return top10(JobAd::getCity);
    }

    private Aggregation top10(Function<JobAd, String> field) {
        List<AggregationDetails> details = jobAds.stream()
            .collect(Collectors.groupingBy(field, Collectors.counting()))
            .entrySet()
            .stream()
            .map(entry -> new AggregationDetails(entry.getKey(), entry.getValue()))
            .sorted(Comparator.comparing(AggregationDetails::occurrences).reversed())
            .toList();
        return new Aggregation(details);
    }

    public void init(List<JobAd> jobAds) {
        this.jobAds = jobAds;
    }

}
