package com.flb.jobfetcher.presentation;

import com.flb.jobfetcher.domain.model.Aggregation;
import com.flb.jobfetcher.domain.model.AggregationDetails;
import com.flb.jobfetcher.domain.application.SyncJobAd;
import com.flb.jobfetcher.domain.model.JobAdStatistics;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestController
public class SyncJobAdController extends ResponseEntityExceptionHandler {

    private final SyncJobAd useCase;

    public SyncJobAdController(SyncJobAd useCase) {
        this.useCase = useCase;
    }

    @PostMapping(value = "/api/job-ads/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobAdStatisticsResponse> sync() {
        JobAdStatistics statistics = useCase.handle();
        JobAdStatisticsResponse response = toPresentation(statistics);
        return ResponseEntity.ok(response);
    }

    private JobAdStatisticsResponse toPresentation(JobAdStatistics statistics) {
        return new JobAdStatisticsResponse(
            statistics.totalJobAds(),
            toPresentation(statistics.topContractTypes()),
            toPresentation(statistics.top10Cities())
        );
    }

    private List<AggregationDto> toPresentation(Aggregation aggregation) {
        return aggregation.values().stream()
            .map(this::toPresentation)
            .toList();
    }

    private AggregationDto toPresentation(AggregationDetails domain) {
        return new AggregationDto(domain.value(), domain.occurrences());
    }

    @ExceptionHandler({WebClientResponseException.Unauthorized.class})
    public ProblemDetail handleUnauthorizedException(WebClientResponseException.Unauthorized exception) {
        return ProblemDetail.forStatusAndDetail(exception.getStatusCode(), exception.getMessage());
    }
}
