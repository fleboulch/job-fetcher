package com.flb.jobfetcher.infra.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@Configuration
@EnableConfigurationProperties(PoleEmploiConfiguration.class)
public class WebConfiguration {

    private final PoleEmploiConfiguration poleEmploiConfiguration;

    public WebConfiguration(PoleEmploiConfiguration poleEmploiConfiguration) {
        this.poleEmploiConfiguration = poleEmploiConfiguration;
    }

    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrations,
                                                          OAuth2AuthorizedClientRepository authorizedClientRepository) {
        OAuth2AuthorizedClientProvider authorizedClientProvider =
            OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();
        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
            clientRegistrations, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    ClientRegistrationRepository clientRegistrations(
        @Value("${spring.security.oauth2.client.provider.flo.token-uri}") String tokenUri,
        @Value("${spring.security.oauth2.client.registration.flo.client-id}") String clientId,
        @Value("${spring.security.oauth2.client.registration.flo.client-secret}") String clientSecret,
        @Value("${spring.security.oauth2.client.registration.flo.scope}") List<String> scopes,
        @Value("${spring.security.oauth2.client.registration.flo.authorization-grant-type}") String authorizationGrantType
    ) {
        ClientRegistration registration = ClientRegistration
            .withRegistrationId("flo")
            .tokenUri(tokenUri)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .scope(scopes)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .build();
        return new InMemoryClientRegistrationRepository(registration);
    }

    @Bean
    public WebClient webClient(
        ObjectMapper objectMapper,
        OAuth2AuthorizedClientManager authorizedClientManager
    ) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
            new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
         oauth2.setDefaultClientRegistrationId("flo");

        return WebClient.builder()
            .baseUrl(poleEmploiConfiguration.getUrl())
//            .filter(oauth2)
            .apply(oauth2.oauth2Configuration())
            .exchangeStrategies(ExchangeStrategies
                .builder()
                .codecs(codecs -> codecs
                    .defaultCodecs()
                    .maxInMemorySize(500 * 1024))
                .build())
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
