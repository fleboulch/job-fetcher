package com.flb.jobfetcher.infra.storage;

import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.infra.storage.jpa.JobAdJpaRepository;
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
    class Count {

        @Test
        void should_return_all_job_ads() {
            // given
            jpaRepository.saveAll(List.of(
                new JobAdJpa("random", "Web developer")
            ));

            // when
            long totalJobAds = repository.count();

            // then
            assertThat(totalJobAds).isEqualTo(1);

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

    @Nested
    class DeleteAll {
        @Test
        void should_sync_job_ads() {
            // given
            jpaRepository.saveAll(List.of(
                new JobAdJpa("random", "Web developer"),
                new JobAdJpa("random 2", "DevOps")
            ));

            // when
            repository.deleteAll();

            // then
            assertThat(jpaRepository.count()).isZero();
        }

    }

}
