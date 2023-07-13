package com.flb.jobfetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.flb.jobfetcher.infra.storage.jpa")
public class JobFetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobFetcherApplication.class, args);
	}

}
