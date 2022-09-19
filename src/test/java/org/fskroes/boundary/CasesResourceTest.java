package org.fskroes.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fskroes.client.CovidClient;
import org.fskroes.helper.StubbedResponseMapper;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;


@QuarkusTest
//@QuarkusTestResource(WireMockExtensions.class)
class CasesResourceTest {

    private static final String COUNTRY_COVID_CASES = "France";
    private static final String CONTINENT_COVID_CASES = "Europe";

    @InjectMock
    @RestClient
    CovidClient covidClient;

    @Inject
    CovidCasesResource covidCasesResource;

    @Inject
    StubbedResponseMapper stubbedResponseMapper;


    @Test
    void getAllLiveCases_executeCorrectly_returnsAllCovidCasus() {
        doReturn(stubbedResponseMapper.getStubContinentResponse())
                .when(covidClient)
                .getAllLiveCases();

        var response = covidCasesResource.getAllLiveCases();

        assertNotNull(response);
    }

    @Test
    void getLiveCasesPerCountry_executeWithCorrectCountryParam_returnsCovidCasusForGivenCountry() {
        doReturn(stubbedResponseMapper.getStubContinentResponse())
                .when(covidClient)
                .getLiveCasesPerCountry(eq(COUNTRY_COVID_CASES));

        var response = covidCasesResource.getLiveCasesPerCountry(COUNTRY_COVID_CASES);

        assertNotNull(response);
    }

    @Test
    void getLiveCasesForContinent_executeWithCorrectContinentParam_returnsCovidCasusForGivenContinent() {
        doReturn(stubbedResponseMapper.getStubContinentResponse())
                .when(covidClient)
                .getLiveCasesForContinent(eq(CONTINENT_COVID_CASES));

        var response = covidCasesResource.getLiveCasesForContinent(CONTINENT_COVID_CASES);

        assertNotNull(response);
    }
}