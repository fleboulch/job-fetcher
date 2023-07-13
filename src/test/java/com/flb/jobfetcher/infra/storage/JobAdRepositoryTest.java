package com.flb.jobfetcher.infra.storage;

import com.flb.jobfetcher.domain.domain.JobAd;
import com.flb.jobfetcher.infra.storage.jpa.JobAdJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ContainersConfiguration.class})
@ActiveProfiles("test")
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
    class FindAll {

        @Test
        void should_return_all_job_ads() {
            // given
            jpaRepository.saveAll(List.of(
                new JobAdJpa("random", "Web developer")
            ));

            // when
            List<JobAd> jobAds = repository.findAll();

            // then
            assertThat(jobAds).containsExactlyInAnyOrder(
                new JobAd("random", "Web developer")
            );

        }
    }

    @Nested
    class Sync {

        @Test
        void should_sync_job_ads() {
            // when
            repository.sync(List.of(
                new JobAd("ignored 1", "Web developer"),
                new JobAd("ignored 2", "DevOps")
            ));

            // then
            assertThat(jpaRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(
                    new JobAdJpa("ignored 1", "Web developer"),
                    new JobAdJpa("ignored 2", "DevOps")
                );
        }
    }

}
