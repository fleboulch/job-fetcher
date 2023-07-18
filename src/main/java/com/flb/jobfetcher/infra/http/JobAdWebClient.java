package com.flb.jobfetcher.infra.http;

import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.domain.model.JobAdFetcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobAdWebClient implements JobAdFetcher {

    private static final String GIRONDE_DEPARTMENT = "33";
    private static final String RANGE_RESULTS = "0-99";
    private final PoleEmploiWebClient webClient;

    public JobAdWebClient(PoleEmploiWebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<JobAd> fetch() {
        ResponseEntity<JobAdResponseDto> jobAds = webClient.searchJobAds(GIRONDE_DEPARTMENT, RANGE_RESULTS);
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
            dto.getIntitule(),
            dto.getTypeContrat(),
            dto.getLieuTravail().getLibelle()
        );
    }

}
