package com.flb.jobfetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestJobFetcherApplication {

	public static void main(String[] args) {
		SpringApplication.from(JobFetcherApplication::main).with(TestJobFetcherApplication.class).run(args);
	}

}
