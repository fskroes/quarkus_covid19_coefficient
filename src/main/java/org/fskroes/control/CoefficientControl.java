package org.fskroes.control;

import org.fskroes.entity.Country;
import org.fskroes.entity.CountryReport;
import org.fskroes.model.CalculationReport;
import org.fskroes.model.CaseCoefficient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ApplicationScoped
public class CoefficientControl {

    @Inject
    CoefficientCalculator coefficientCalculator;

    public CaseCoefficient getCoefficient(
            String continent,
            Map<String, Country> givenContinentCase,
            Map<String, Country> givenContinentVaccineCase
    ) {

        var allConfirmedOfContinent = mapToSumOfValue(givenContinentCase)
                .mapToLong(CountryReport::getConfirmed)
                .sum();
        var allRecoveredOfContinent = mapToSumOfValue(givenContinentCase)
                .mapToLong(CountryReport::getRecovered)
                .sum();
        var allDeathsOfContinent = mapToSumOfValue(givenContinentCase)
                .mapToLong(CountryReport::getDeaths)
                .sum();
        var allPopulationOfContinent = mapToSumOfValue(givenContinentCase)
                .mapToLong(CountryReport::getPopulation)
                .sum();
        var allVaccinatedOfContinent = mapToSumOfValue(givenContinentVaccineCase)
                .mapToLong(CountryReport::getVaccinated)
                .sum();

        var calculationReport = CalculationReport
                .builder()
                .name(continent)
                .sumConfirmed(allConfirmedOfContinent)
                .sumRecovered(allRecoveredOfContinent)
                .sumDeath(allDeathsOfContinent)
                .sumPopulation(allPopulationOfContinent)
                .vaccinated(allVaccinatedOfContinent)
                .build();


        return coefficientCalculator.getCoefficientCalculator(calculationReport);
    }

    private Stream<CountryReport> mapToSumOfValue(Map<String, Country> map) {
        return map
                .values()
                .stream()
                .map(Country::getAll);
    }

    private Map<String, String> getContinentReport(CountryReport countryReport) {
        Map<String, String> report = new HashMap<>();
        report.put("country", countryReport.getCountry());
        report.put("confirmed", String.valueOf(countryReport.getConfirmed()));
        report.put("recovered", String.valueOf(countryReport.getRecovered()));
        report.put("deaths", String.valueOf(countryReport.getDeaths()));
        report.put("population", String.valueOf(countryReport.getPopulation()));

        return report;
    }
}
