package com.flb.jobfetcher.infra.http;

import com.flb.jobfetcher.domain.Aggregation;
import com.flb.jobfetcher.domain.AggregationDetails;
import com.flb.jobfetcher.domain.model.JobAd;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.util.Pair;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@RestClientTest
@Import({
    WebConfiguration.class,
    JobAdWebClient.class
})
class JobAdWebClientTest {

    @Autowired
    private JobAdWebClient webClient;

    @RegisterExtension
    static WireMockExtension WIREMOCK_SERVER = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort())
        .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("pole-emploi.url", WIREMOCK_SERVER::baseUrl);
    }

    @Test
    void it_should_fetch_jobs_from_pole_emploi_api() {
        // given
        PoleEmploiApiUtils.getJobAds();

        // when
        Pair<List<JobAd>, Aggregation> jobAds = webClient.fetch();

        // then
        assertThat(jobAds).isEqualTo(Pair.of(
                List.of(
                    new JobAd("17", "Web developer"),
                    new JobAd("18", "Devops")
                ),
                new Aggregation(List.of(
                    new AggregationDetails("CDI", 13371),
                    new AggregationDetails("MIS", 6045),
                    new AggregationDetails("CDD", 3913),
                    new AggregationDetails("LIB", 411),
                    new AggregationDetails("SAI", 260),
                    new AggregationDetails("FRA", 178),
                    new AggregationDetails("CCE", 54),
                    new AggregationDetails("DIN", 25),
                    new AggregationDetails("DDI", 9)
                ))
            )
        );

    }
}
