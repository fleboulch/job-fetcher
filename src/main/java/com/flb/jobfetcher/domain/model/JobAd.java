package com.flb.jobfetcher.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class JobAd {

    private String id;
    private String title;
    private String contractType;
    private String city;
}
