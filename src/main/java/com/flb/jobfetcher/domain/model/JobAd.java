package com.flb.jobfetcher.domain.model;

public record JobAd(
    String id,
    String title,
    String contractType,
    String city
) { }
