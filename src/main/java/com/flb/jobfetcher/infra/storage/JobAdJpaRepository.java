package com.flb.jobfetcher.infra.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobAdJpaRepository extends JpaRepository<JobAdJpa, String> {

    @Query("""
        SELECT jpa.contractType AS name, COUNT(jpa.contractType) AS total
        FROM JobAdJpa AS jpa
        GROUP BY jpa.contractType
        ORDER BY total DESC
        LIMIT 10
    """)
    List<AggregationViewJpa> findTop10ContractTypes();

    @Query("""
        SELECT jpa.city AS name, COUNT(jpa.city) AS total
        FROM JobAdJpa AS jpa
        GROUP BY jpa.city
        ORDER BY total DESC
        LIMIT 10
    """)
    List<AggregationViewJpa> findTop10Cities();
}
