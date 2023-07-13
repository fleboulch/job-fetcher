package com.flb.jobfetcher.infra.http;

import com.flb.jobfetcher.domain.model.JobAd;
import com.flb.jobfetcher.infra.storage.ContainersConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({
    ContainersConfiguration.class,
    WebConfiguration.class,
})
//@RestClientTest
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
        List<JobAd> jobAds = webClient.fetch();

        // then
        assertThat(jobAds).containsExactlyInAnyOrder(
            new JobAd("17", "Web developer"),
            new JobAd("18", "Devops")
        );

    }
}
