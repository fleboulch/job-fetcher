package com.flb.jobfetcher.presentation;

import com.flb.jobfetcher.domain.application.SyncJobAd;
import com.flb.jobfetcher.domain.model.JobAdStatistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {SyncJobAdController.class})
@ExtendWith(MockitoExtension.class)
class SyncJobAdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SyncJobAd useCase;

    @Test
    void it_should_return_the_statistics_after_a_run() throws Exception {
        // given
        when(useCase.handle()).thenReturn(new JobAdStatistics(10));

        // when
        mockMvc.perform(post("/api/job-ads/sync")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(
                //language=json
                """
                {
                    "total": 10
                }
                """
            ));
    }
}
