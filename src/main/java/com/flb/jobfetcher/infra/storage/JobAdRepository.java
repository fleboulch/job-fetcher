package com.flb.jobfetcher.infra.storage;

import com.flb.jobfetcher.domain.domain.JobAd;
import com.flb.jobfetcher.domain.domain.JobAdStorage;
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
            domain.getTitle()
        );
    }

    @Override
    public List<JobAd> findAll() {
        return jpaRepository.findAll()
            .stream()
            .map(this::toDomain)
            .toList();
    }

    private JobAd toDomain(JobAdJpa jpa) {
        return new JobAd(
            jpa.getId(),
            jpa.getTitle()
        );
    }
}
