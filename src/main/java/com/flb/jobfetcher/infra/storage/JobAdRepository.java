package com.flb.jobfetcher.infra.storage;

import com.flb.jobfetcher.domain.model.*;
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
            domain.id(),
            domain.title(),
            domain.contractType(),
            domain.city()
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
        List<AggregationViewJpa> top10CitiesJpa = jpaRepository.findTop10Cities();

        return new JobAdStatistics(
            countJobAds,
            toDomain(top10ContractTypesJpa),
            toDomain(top10CitiesJpa)
        );
    }

    private Aggregation toDomain(List<AggregationViewJpa> top10ContractTypesJpa) {
        List<AggregationDetails> aggregationDetails = top10ContractTypesJpa.stream()
            .map(details -> new AggregationDetails(details.getName(), details.getTotal()))
            .toList();
        return new Aggregation(aggregationDetails);
    }

}
