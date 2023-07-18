package com.flb.jobfetcher.presentation;

import com.flb.jobfetcher.domain.model.Aggregation;
import com.flb.jobfetcher.domain.model.AggregationDetails;
import com.flb.jobfetcher.domain.application.SyncJobAd;
import com.flb.jobfetcher.domain.model.JobAdStatistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

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
        when(useCase.handle()).thenReturn(new JobAdStatistics(
            1000,
            new Aggregation(List.of(
                new AggregationDetails("CDI", 800),
                new AggregationDetails("MIS", 110),
                new AggregationDetails("DDI", 90)
            )),
            new Aggregation(List.of(
                new AggregationDetails("33 - Bordeaux", 750),
                new AggregationDetails("33 - Lormont", 250)
            ))));

        // when
        mockMvc.perform(post("/api/job-ads/sync")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(
                //language=json
                """
                {
                    "total": 1000,
                    "topContractTypes": [
                        {
                            "name": "CDI",
                            "occurrences": 800
                        },
                        {
                            "name": "MIS",
                            "occurrences": 110
                        },
                        {
                            "name": "DDI",
                            "occurrences": 90
                        }
                    ],
                    "topCities": [
                        {
                            "name": "33 - Bordeaux",
                            "occurrences": 750
                        },
                        {
                            "name": "33 - Lormont",
                            "occurrences": 250
                        }
                    ]
                }
                """
            ));
    }

    @Test
    void it_should_return_unauthorized_exception() throws Exception {
        // given
        when(useCase.handle()).thenThrow(buildUnauthorizedException());

        // when
        mockMvc.perform(post("/api/job-ads/sync")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    private WebClientResponseException buildUnauthorizedException() {
        return WebClientResponseException.create(HttpStatus.UNAUTHORIZED.value(), null, null, null, null);
    }
}
