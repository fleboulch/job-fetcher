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
    private SyncJobAd useCase = new SyncJobAd(fetcher);

    @Nested
    class FullMode {
        @Test
        void it_should_sync_job_ad() {
            // given
            fetcher.init(List.of(
                new JobAd(),
                new JobAd()
            ));

            // when
            JobAdStatistics statistics = useCase.handle(SyncMode.FULL);

            // then
            assertThat(statistics).isEqualTo(new JobAdStatistics(2));
        }

    }
}
