package org.fskroes.client;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fskroes.entity.Country;
import org.fskroes.entity.CountryReport;
import org.fskroes.helper.StubbedResponseMapper;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        var responseCountry = getMatchedCountry(countries, expectedResult.get("All"));
        assertTrue(responseCountry.isPresent());

        assertNotNull(response);
        assertNotNull(expectedResult);
        assertEquals(
                expectedResult.get("All").getAll().getCountry(),
                responseCountry.get().getAll().getCountry()
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

        var responseCountry = getMatchedCountry(countries, expectedResult.get("All"));
        assertTrue(responseCountry.isPresent());

        assertNotNull(response);
        assertNotNull(expectedResult);
        assertEquals(
                expectedResult.get("All").getAll().getCountry(),
                responseCountry.get().getAll().getCountry()
        );
    }

    @Test
    void getAllLiveCases_correctParams_returnsAllCases() {
        doReturn(stubbedResponseMapper.getStubAllCountriesResponse())
                .when(covidClient)
                .getAllLiveCases();

        var response = covidClient.getAllLiveCases();

        assertNotNull(response);
        assertTrue(response.values().size() > 1);
    }

    private Optional<Country> getMatchedCountry(List<Country> listOfCountriesToSearch, Country expectedCountry) {
        return listOfCountriesToSearch
                .stream()
                .filter(country -> country.getAll().getCountry().equals(expectedCountry.getAll().getCountry()))
                .findFirst();
    }

    private Map<String, Country> getExpectedMappedJsonResult() {
        return Map.of(
                "All", getStubCountry()
        );
    }

    private Country getStubCountry() {
        var expectedReport = CountryReport
                .builder()
                .confirmed(33884825)
                .recovered(342253)
                .deaths(151169)
                .country("France")
                .population(64979548)
                .area(551500)
                .lifeExpectancy(78.8)
                .elevation("375")
                .continent("Europe")
                .abbreviation("FR")
                .location("Western Europe")
                .iso(250)
                .capitalCity("Paris")
                .latitude(46.2276)
                .longitude(2.2137)
                .updated("2022-09-18 04:22:50")
                .build();

        var country = new Country();
        country.setAll(expectedReport);
        return country;
    }
}
