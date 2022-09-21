package org.fskroes.client;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fskroes.entity.Country;
import org.fskroes.entity.CountryReport;
import org.fskroes.helper.StubbedResponseMapper;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@QuarkusTest
public class CovidClientTest {

    private static final String CONTINENT = "Europe";

    @InjectMock
    @RestClient
    CovidClient covidClient;

    @Inject
    StubbedResponseMapper stubbedResponseMapper;

    @Test
    void getLiveCasesForContinent_correctContinentParam_returnCovidCaseForGivenContinent() {
        doReturn(stubbedResponseMapper.getStubContinentResponse())
                .when(covidClient)
                .getLiveCasesForContinent(anyString());

        var expectedResult = getExpectedMappedJsonResult();

        var response = covidClient.getLiveCasesForContinent(CONTINENT);

        var countries = response
                .values()
                .stream()
                .toList();

        var responseCountry = countries
                .stream()
                .filter(country -> country.getAll().getCountry().equals(expectedResult.get("All").getAll().getCountry()))
                .findFirst()
                .get();

        assertNotNull(response);
        assertNotNull(expectedResult);
        assertEquals(
                expectedResult.get("All").getAll().getCountry(),
                responseCountry.getAll().getCountry()
        );
    }

    @Test
    void getCasesVaccineForContinent_correctContinentParam_returnCovidCaseForGivenContinent() {
        doReturn(stubbedResponseMapper.getStubVaccineContinentResponse())
                .when(covidClient)
                .getCasesVaccineForContinent(anyString());

        var expectedResult = getExpectedMappedJsonResult();

        var response = covidClient.getCasesVaccineForContinent(CONTINENT);

        var countries = response
                .values()
                .stream()
                .toList();

        var responseCountry = countries
                .stream()
                .filter(country -> country.getAll().getCountry().equals(expectedResult.get("All").getAll().getCountry()))
                .findFirst()
                .get();

        assertNotNull(response);
        assertNotNull(expectedResult);
        assertEquals(
                expectedResult.get("All").getAll().getCountry(),
                responseCountry.getAll().getCountry()
        );
    }

    private Map<String, Country> getExpectedMappedJsonResult() {
        return Map.of(
                "All", getStubCountry()
        );
    }

    private Country getStubCountry() {
        var expectedReport = new CountryReport();
        expectedReport.setConfirmed(33884825);
        expectedReport.setRecovered(342253);
        expectedReport.setDeaths(151169);
        expectedReport.setCountry("France");
        expectedReport.setPopulation(64979548);
        expectedReport.setArea(551500);
        expectedReport.setLifeExpectancy(78.8);
        expectedReport.setElevation("375");
        expectedReport.setContinent("Europe");
        expectedReport.setAbbreviation("FR");
        expectedReport.setLocation("Western Europe");
        expectedReport.setIso(250);
        expectedReport.setCapitalCity("Paris");
        expectedReport.setLatitude(46.2276);
        expectedReport.setLongitude(2.2137);
        expectedReport.setUpdated("2022-09-18 04:22:50");

        var expectedCountry = new Country();
        expectedCountry.setAll(expectedReport);

        return expectedCountry;
    }
}
