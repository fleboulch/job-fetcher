package com.flb.jobfetcher.infra.storage;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgreContainer() {
        return new PostgreSQLContainer<>("postgres:15.2-alpine")
            .withDatabaseName("job")
            .withUsername("admin")
            .withPassword("change-me"); // the strong password should be in an environment variable in a real application for security reasons
    }
}
