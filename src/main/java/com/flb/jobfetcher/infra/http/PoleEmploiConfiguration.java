package com.flb.jobfetcher.infra.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pole-emploi")
@Getter
@Setter
public class PoleEmploiConfiguration {
    private String url;
}
