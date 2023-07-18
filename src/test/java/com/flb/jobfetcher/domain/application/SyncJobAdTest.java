package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.Aggregation;
import com.flb.jobfetcher.domain.AggregationDetails;
import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.domain.model.JobAdStatistics;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SyncJobAdTest {

    private JobAdFetcherStub fetcher = new JobAdFetcherStub();
    private JobAdStorageStub storage = new JobAdStorageStub();
    private SyncJobAd useCase = new SyncJobAd(fetcher, storage);

    @Test
    void it_should_sync_job_ad() {
        // given
        fetcher.init(List.of(
            new JobAd("1", "Web developer"),
            new JobAd("2", "Devops")
        ));
        storage.init(List.of(
            new JobAd("3", "Web developer"),
            new JobAd("4", "Devops")
        ));

        // when
        JobAdStatistics statistics = useCase.handle();

        // then
        assertThat(statistics).isEqualTo(new JobAdStatistics(
            2,
            new Aggregation(List.of(
                new AggregationDetails("CDI", 10),
                new AggregationDetails("MIS", 5),
                new AggregationDetails("DDI", 2)
            ))));
    }

}
