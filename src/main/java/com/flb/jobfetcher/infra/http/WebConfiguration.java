package com.flb.jobfetcher.infra.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableConfigurationProperties(PoleEmploiConfiguration.class)
public class WebConfiguration {

    private final PoleEmploiConfiguration poleEmploiConfiguration;

    public WebConfiguration(PoleEmploiConfiguration poleEmploiConfiguration) {
        this.poleEmploiConfiguration = poleEmploiConfiguration;
    }

    @Bean
    public WebClient webClient(ObjectMapper objectMapper) {
        return WebClient.builder()
            .baseUrl(poleEmploiConfiguration.getUrl())
            .build();
    }

    @Bean
    public PoleEmploiWebClient poleEmploiWebClient(WebClient webClient) {
        var httpServiceProxyFactory = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(webClient))
            .build();
        return httpServiceProxyFactory.createClient(PoleEmploiWebClient.class);
    }
}
