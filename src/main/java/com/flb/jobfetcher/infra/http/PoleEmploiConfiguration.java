package com.flb.jobfetcher.infra.http;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "pole-emploi")
@Getter
@Setter
@Validated
public class PoleEmploiConfiguration {
    private String url;
    @NotBlank
    private String accessToken;
}
