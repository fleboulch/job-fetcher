package com.flb.jobfetcher.infra.storage;

import com.flb.jobfetcher.domain.model.Aggregation;
import com.flb.jobfetcher.domain.model.AggregationDetails;
import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.domain.model.JobAdStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ContainersConfiguration.class})
class JobAdRepositoryTest {

    @Autowired
    private JobAdJpaRepository jpaRepository;

    @Autowired
    private JobAdRepository repository;

    @BeforeEach
    void setUp() {
        jpaRepository.deleteAll();
    }

    @Nested
    class Sync {

        @Test
        void should_sync_job_ads() {
            // when
            repository.sync(List.of(
                new JobAd("ignored 1", "Web developer", "CDD", "Bordeaux"),
                new JobAd("ignored 2", "DevOps", "CDD", "Bordeaux")
            ));

            // then
            assertThat(jpaRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(
                    new JobAdJpa("ignored 1", "Web developer", "CDD", "Bordeaux"),
                    new JobAdJpa("ignored 2", "DevOps", "CDD", "Bordeaux")
                );
        }
    }

    @Nested
    class DeleteAll {
        @Test
        void should_sync_job_ads() {
            // given
            jpaRepository.saveAll(List.of(
                new JobAdJpa("random", "Web developer", "CDD", "Bordeaux"),
                new JobAdJpa("random 2", "DevOps", "CDD", "Bordeaux")
            ));

            // when
            repository.deleteAll();

            // then
            assertThat(jpaRepository.count()).isZero();
        }

    }

    @Nested
    class ComputeStatistics {

        @Test
        void name() {
            // given
            jpaRepository.saveAll(List.of(
                new JobAdJpa("random", "DevOps", "CDI", "Lormont"),
                new JobAdJpa("random 1", "Web developer", "CDD", "Bordeaux"),
                new JobAdJpa("random 2", "DevOps", "CDD", "Bordeaux")
            ));

            // when
            JobAdStatistics jobAdStatistics = repository.computeStatistics();

            // then
            assertThat(jobAdStatistics).isEqualTo(
                new JobAdStatistics(
                    3,
                    new Aggregation(List.of(
                        new AggregationDetails("CDD", 2),
                        new AggregationDetails("CDI", 1)
                    )),
                    new Aggregation(List.of(
                        new AggregationDetails("Bordeaux", 2),
                        new AggregationDetails("Lormont", 1)
                    )))
            );
        }
    }

}
