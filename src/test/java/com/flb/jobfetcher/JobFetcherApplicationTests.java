package com.flb.jobfetcher;

import com.flb.jobfetcher.infra.storage.ContainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({ContainersConfiguration.class})
class JobFetcherApplicationTests {

	@Test
	void contextLoads() {
	}

}
