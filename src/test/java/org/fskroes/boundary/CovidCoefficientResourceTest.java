package org.fskroes.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fskroes.client.CovidClient;
import org.fskroes.helper.StubbedResponseMapper;
import org.fskroes.model.CaseCoefficient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@QuarkusTest
public class CovidCoefficientResourceTest {

    private static final String CONTINENT = "Europe";

    @InjectMock
    @RestClient
    CovidClient covidClient;

    @Inject
    CovidCoefficientResource covidCoefficientResource;

    @Inject
    StubbedResponseMapper stubbedResponseMapper;

    @Test
    void getCoefficientForCountry_givenCountry_returnsCoefficientCalculationForCountry() {

        var expectedCaseCoefficient = CaseCoefficient
                .builder()
                .name(CONTINENT)
                .coefficient(0.0)
                .build();

        var stubCountryResponse = stubbedResponseMapper
                .getStubContinentResponse();

        var stubVaccineResponse = stubbedResponseMapper
                .getStubVaccineContinentResponse();


        doReturn(stubCountryResponse)
                .when(covidClient)
                .getLiveCasesForContinent(any());

        doReturn(stubVaccineResponse)
                .when(covidClient)
                .getCasesVaccineForContinent(any());


        var response = covidCoefficientResource
                .getCoefficientForContinent(CONTINENT);


        assertNotNull(response);
        assertEquals(expectedCaseCoefficient.getCoefficient(), response.await().atMost(Duration.ofSeconds(5)).getCoefficient());
    }
}
