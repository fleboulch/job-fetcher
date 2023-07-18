package com.flb.jobfetcher.domain.application;

import com.flb.jobfetcher.domain.model.Aggregation;
import com.flb.jobfetcher.domain.model.AggregationDetails;
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
            new JobAd("4", "Devops", "CDI", "Bordeaux"),
            new JobAd("1", "Web developer", "CDD", "Bordeaux"),
            new JobAd("2", "Devops", "CDD", "Bordeaux")
        ));
        storage.init(List.of(
            new JobAd("3", "Web developer", "CDD", "Bordeaux"),
            new JobAd("4", "Devops", "CDD", "Bordeaux")
        ));

        // when
        JobAdStatistics statistics = useCase.handle();

        // then
        assertThat(statistics).isEqualTo(new JobAdStatistics(
            3,
            new Aggregation(List.of(
                new AggregationDetails("CDD", 2),
                new AggregationDetails("CDI", 1)
            )),
            new Aggregation(List.of(
                new AggregationDetails("Bordeaux", 3)
            ))
        ));
    }

}
