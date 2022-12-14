package org.fskroes.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fskroes.client.CovidClient;
import org.fskroes.control.CoefficientControl;
import org.fskroes.helper.StubbedResponseMapper;
import org.fskroes.model.CaseCoefficient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;


@QuarkusTest
public class CovidCoefficientResourceTest {

    private static final String CONTINENT = "Europe";

    @InjectMock
    @RestClient
    CovidClient covidClient;

    @InjectMock
    CoefficientControl coefficientControl;

    @Inject
    CovidCoefficientResource covidCoefficientResource;

    @Inject
    StubbedResponseMapper stubbedResponseMapper;

    @Test
    void getCoefficientForCountry_givenCorrectContinentParams_returnsCoefficientCalculationForCountry() {

        var expectedCaseCoefficient = CaseCoefficient
                .builder()
                .name(CONTINENT)
                .coefficient(1.5366971809287894E25)
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

        doReturn(expectedCaseCoefficient)
                .when(coefficientControl)
                .getCoefficientForContinent(CONTINENT, stubCountryResponse, stubVaccineResponse);

        var response = covidCoefficientResource
                .getCoefficientForContinent(CONTINENT);


        assertNotNull(response);
        var awaitedResponse = response.await().atMost(Duration.ofSeconds(5)).getCoefficient();
        assertEquals(expectedCaseCoefficient.getCoefficient(), awaitedResponse);
    }

    @Test
    void getCoefficientForAllCountries_givenCorrectPath_returnCalculatedCoefficientForAllCountries() {
        var expectedCaseCoefficient = CaseCoefficient
                .builder()
                .name(CONTINENT)
                .coefficient(0.45678)
                .build();

        var stubAllCountriesResponse = stubbedResponseMapper
                .getStubAllCountriesResponse();

        var stubAllVaccinedResponse = stubbedResponseMapper
                .getStubAllVaccineCaseResponse();

        doReturn(stubAllCountriesResponse)
                .when(covidClient)
                .getAllLiveCases();

        doReturn(stubAllVaccinedResponse)
                .when(covidClient)
                .getCasesVaccineForContinent(any());

        doReturn(expectedCaseCoefficient)
                .when(coefficientControl)
                .getCoefficientForAllCountries(any(), anyList());

        var response = covidCoefficientResource
                .getCoefficientForAllCountries();

        assertNotNull(response);
        var awaitedResponse = response.await().atMost(Duration.ofSeconds(5)).getCoefficient();
        assertEquals(expectedCaseCoefficient.getCoefficient(), awaitedResponse);
    }
}
