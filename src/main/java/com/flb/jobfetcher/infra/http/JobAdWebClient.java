package com.flb.jobfetcher.infra.http;

import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.domain.model.JobAdFetcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobAdWebClient implements JobAdFetcher {

    private final PoleEmploiWebClient webClient;

    public JobAdWebClient(PoleEmploiWebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<JobAd> fetch() {
        ResponseEntity<JobAdResponseDto> jobAds = webClient.searchJobAds("33");
        return toDomain(jobAds.getBody());
    }

    private List<JobAd> toDomain(JobAdResponseDto dto) {
        return dto.getResultats().stream()
            .map(this::toDomain)
            .toList();
    }

    private JobAd toDomain(JobAdDto dto) {
        return new JobAd(
            dto.getId(),
            dto.getIntitule()
        );
    }
}
