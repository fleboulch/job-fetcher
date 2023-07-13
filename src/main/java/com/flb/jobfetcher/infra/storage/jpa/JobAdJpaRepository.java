package com.flb.jobfetcher.infra.storage.jpa;

import com.flb.jobfetcher.infra.storage.JobAdJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobAdJpaRepository extends JpaRepository<JobAdJpa, String> {
}
