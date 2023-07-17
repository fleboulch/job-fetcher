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
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                            //language=json
                            """
                                    {
                                        "resultats": [
                                            {
                                                "id": "17",
                                                "intitule": "Web developer"
                                            },
                                            {
                                                "id": "18",
                                                "intitule": "Devops"
                                            }
                                        ],
                                        "filtresPossibles": [
                                            {
                                                "filtre": "typeContrat",
                                                "agregation": [
                                                    {
                                                        "valeurPossible": "CCE",
                                                        "nbResultats": 54
                                                    },
                                                    {
                                                        "valeurPossible": "CDD",
                                                        "nbResultats": 3913
                                                    },
                                                    {
                                                        "valeurPossible": "CDI",
                                                        "nbResultats": 13371
                                                    },
                                                    {
                                                        "valeurPossible": "DDI",
                                                        "nbResultats": 9
                                                    },
                                                    {
                                                        "valeurPossible": "DIN",
                                                        "nbResultats": 25
                                                    },
                                                    {
                                                        "valeurPossible": "FRA",
                                                        "nbResultats": 178
                                                    },
                                                    {
                                                        "valeurPossible": "LIB",
                                                        "nbResultats": 411
                                                    },
                                                    {
                                                        "valeurPossible": "MIS",
                                                        "nbResultats": 6045
                                                    },
                                                    {
                                                        "valeurPossible": "SAI",
                                                        "nbResultats": 260
                                                    }
                                                ]
                                            },
                                            {
                                                "filtre": "qualification",
                                                "agregation": [
                                                    {
                                                        "valeurPossible": "0",
                                                        "nbResultats": 10708
                                                    },
                                                    {
                                                        "valeurPossible": "9",
                                                        "nbResultats": 1211
                                                    },
                                                    {
                                                        "valeurPossible": "X",
                                                        "nbResultats": 12347
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                """
                        )
                )
        );

    }
}
