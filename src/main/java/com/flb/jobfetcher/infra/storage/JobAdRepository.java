package com.flb.jobfetcher.infra.storage;

import com.flb.jobfetcher.domain.model.*;
import com.flb.jobfetcher.infra.storage.jpa.AggregationViewJpa;
import com.flb.jobfetcher.infra.storage.jpa.JobAdJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobAdRepository implements JobAdStorage {

    private final JobAdJpaRepository jpaRepository;

    public JobAdRepository(JobAdJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void sync(List<JobAd> jobAds) {
        jpaRepository.saveAll(toJpa(jobAds));
    }

    private List<JobAdJpa> toJpa(List<JobAd> domain) {
        return domain.stream()
            .map(this::toJpa)
            .toList();
    }

    private JobAdJpa toJpa(JobAd domain) {
        return new JobAdJpa(
            domain.getId(),
            domain.getTitle(),
            domain.getContractType(),
            domain.getCity()
        );
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    @Override
    public JobAdStatistics computeStatistics() {
        long countJobAds = jpaRepository.count();
        List<AggregationViewJpa> top10ContractTypesJpa = jpaRepository.findTop10ContractTypes();
        Aggregation top10ContractTypes = toDomain(top10ContractTypesJpa);

        List<AggregationViewJpa> top10CitiesJpa = jpaRepository.findTop10Cities();
        Aggregation top10Cities = toDomain(top10CitiesJpa);

        return new JobAdStatistics(countJobAds, top10ContractTypes, top10Cities);
    }

    private Aggregation toDomain(List<AggregationViewJpa> top10ContractTypesJpa) {
        List<AggregationDetails> aggregationDetails = top10ContractTypesJpa.stream()
            .map(details -> new AggregationDetails(details.getName(), details.getTotal()))
            .toList();
        return new Aggregation(aggregationDetails);
    }

}
