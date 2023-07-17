package com.flb.jobfetcher.infra.http;

import com.flb.jobfetcher.domain.Aggregation;
import com.flb.jobfetcher.domain.AggregationDetails;
import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.domain.model.JobAdFetcher;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Repository
public class JobAdWebClient implements JobAdFetcher {

    private final PoleEmploiWebClient webClient;

    public JobAdWebClient(PoleEmploiWebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Pair<List<JobAd>, Aggregation> fetch() {
        ResponseEntity<JobAdResponseDto> jobAds = webClient.searchJobAds("33", "0-99");
        return toDomain(jobAds.getBody());
    }

    private Pair<List<JobAd>, Aggregation> toDomain(JobAdResponseDto dto) {
        List<JobAd> jobAds = dto.getResultats().stream()
            .map(this::toDomain)
            .toList();
        Aggregation typeContratAggregation = dto.getFiltresPossibles().stream()
            .filter(possibleFilter -> Objects.equals(possibleFilter.getFiltre(), "typeContrat"))
            .map(JobAdWebClient::buildAggregation)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("The filter 'typeContrat' has not been found in pole emploi API"));

        return Pair.of(jobAds, typeContratAggregation);
    }

    private JobAd toDomain(JobAdDto dto) {
        return new JobAd(
            dto.getId(),
            dto.getIntitule()
        );
    }

    private static Aggregation buildAggregation(FilterDto filter) {
        return new Aggregation(
            filter.getAgregation().stream()
                .sorted(Comparator.comparing(AggregationFilterDto::getNbResultats).reversed())
                .map(JobAdWebClient::toDomain)
                .limit(10)
                .toList()
        );
    }

    private static AggregationDetails toDomain(AggregationFilterDto dto) {
        return new AggregationDetails(
            dto.getValeurPossible(),
            dto.getNbResultats()
        );
    }
}
