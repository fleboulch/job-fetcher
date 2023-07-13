package com.flb.jobfetcher.infra.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@HttpExchange(contentType = APPLICATION_JSON_VALUE, accept = {APPLICATION_JSON_VALUE})
public interface PoleEmploiWebClient {

    @GetExchange(url = "/v2/offres/search")
    ResponseEntity<JobAdResponseDto> searchJobAds(
        @RequestParam String departement
    );
}
