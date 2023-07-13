package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.domain.JobAd;
import com.flb.jobfetcher.domain.domain.JobAdStatistics;
import com.flb.jobfetcher.domain.domain.SyncMode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SyncJobAdTest {

    private JobAdFetcherStub fetcher = new JobAdFetcherStub();
    private JobAdStorageStub storage = new JobAdStorageStub();
    private SyncJobAd useCase = new SyncJobAd(fetcher, storage);

    @Nested
    class FullMode {
        @Test
        void it_should_sync_job_ad() {
            // given
            List<JobAd> jobAds = List.of(
                new JobAd("1", "Web developer"),
                new JobAd("2", "Devops")
            );
            fetcher.init(jobAds);
            storage.init(jobAds);

            // when
            JobAdStatistics statistics = useCase.handle(SyncMode.FULL);

            // then
            assertThat(storage.isSyncWasCalled()).isTrue();
            assertThat(statistics).isEqualTo(new JobAdStatistics(2));
        }

    }
}
