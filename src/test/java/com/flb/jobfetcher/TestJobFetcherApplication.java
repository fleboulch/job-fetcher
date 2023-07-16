package com.flb.jobfetcher;

import com.flb.jobfetcher.infra.storage.ContainersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestJobFetcherApplication {

	public static void main(String[] args) {
		SpringApplication.from(JobFetcherApplication::main)
            .with(TestJobFetcherApplication.class)
            .with(ContainersConfiguration.class)
            .run(args);
	}

}
