package org.fskroes.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fskroes.client.CovidClient;
import org.fskroes.model.CaseCoefficient;
import org.fskroes.helper.StubbedResponseMapper;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;


@QuarkusTest
public class CovidCoefficientResourceTest {

    private static final String COUNTRY = "France";

    @InjectMock
    @RestClient
    CovidClient covidClient;

    @Inject
    CovidCoefficientResource covidCoefficientResource;

    @Inject
    StubbedResponseMapper stubbedResponseMapper;

    @Test
    void getCoefficientForCountry_givenCountry_returnsCoefficientCalculationForCountry() {

        CaseCoefficient expectedCaseCoefficient = new CaseCoefficient();
        expectedCaseCoefficient.setName(COUNTRY);
        expectedCaseCoefficient.setCoefficient(0.2);

        var stubCountryResponse = stubbedResponseMapper
                .getStubCountryResponse();

        var stubVaccineResponse = stubbedResponseMapper
                .getStubVaccineResponse();


        doReturn(stubCountryResponse)
                .when(covidClient)
                .getLiveCasesPerCountry(any());

        doReturn(stubVaccineResponse)
                .when(covidClient)
                .getCasesVaccineForCountry(any());


        var response = covidCoefficientResource
                .getCoefficientForCountry(COUNTRY);


        assertNotNull(response);
        assertEquals(expectedCaseCoefficient.getCoefficient(), response.await().atMost(Duration.ofSeconds(5)).getCoefficient());
    }
}
