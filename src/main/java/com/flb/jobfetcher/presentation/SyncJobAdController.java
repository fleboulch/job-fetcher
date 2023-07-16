package com.flb.jobfetcher.presentation;

import com.flb.jobfetcher.domain.application.SyncJobAd;
import com.flb.jobfetcher.domain.model.JobAdStatistics;
import com.flb.jobfetcher.domain.model.SyncMode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncJobAdController {

    private final SyncJobAd useCase;

    public SyncJobAdController(SyncJobAd useCase) {
        this.useCase = useCase;
    }

    @PostMapping(value = "/api/job-ads/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobAdStatisticsResponse> sync() {
        JobAdStatistics statistics = useCase.handle(SyncMode.FULL);
        JobAdStatisticsResponse response = toPresentation(statistics);
        return ResponseEntity.ok(response);
    }

    private JobAdStatisticsResponse toPresentation(JobAdStatistics statistics) {
        return new JobAdStatisticsResponse(
            statistics.totalJobAds()
        );
    }
}
