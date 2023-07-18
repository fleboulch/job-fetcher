package com.flb.jobfetcher.infra.http;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.flb.jobfetcher.infra.http.JobAdWebClientTest.WIREMOCK_SERVER;

public class PoleEmploiApiUtils {

    public static void getJobAds() {
        WIREMOCK_SERVER.stubFor(
            WireMock.get("/v2/offres/search?departement=33&range=0-99")
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(HttpStatus.PARTIAL_CONTENT.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                            //language=json
                            """
                                    {
                                        "resultats": [
                                            {
                                                "id": "17",
                                                "intitule": "Web developer",
                                                "typeContrat": "CDD",
                                                "lieuTravail": {
                                                    "libelle": "33 - BORDEAUX"
                                                }
                                            },
                                            {
                                                "id": "18",
                                                "intitule": "Devops",
                                                "typeContrat": "CDD",
                                                "lieuTravail": {
                                                    "libelle": "33 - BORDEAUX"
                                                }
                                            }
                                        ]
                                    }
                                """
                        )
                )
        );

    }
}
