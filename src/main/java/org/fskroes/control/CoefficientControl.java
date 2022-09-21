package org.fskroes.control;

import org.fskroes.entity.Country;
import org.fskroes.entity.CountryReport;
import org.fskroes.model.CalculationReport;
import org.fskroes.model.CaseCoefficient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.stream.Stream;

@ApplicationScoped
public class CoefficientControl {

    @Inject
    CoefficientCalculator coefficientCalculator;

    public CaseCoefficient getCoefficientForContinent(
            String continent,
            Map<String, Country> givenContinentCase,
            Map<String, Country> givenContinentVaccineCase
    ) {

        var allConfirmedOfContinent = mapToSumOfValue(givenContinentCase)
                .map(CountryReport::getConfirmed)
                .toList();
        var allRecoveredOfContinent = mapToSumOfValue(givenContinentCase)
                .map(CountryReport::getRecovered)
                .toList();
        var allDeathsOfContinent = mapToSumOfValue(givenContinentCase)
                .map(CountryReport::getDeaths)
                .toList();
        var allPopulationOfContinent = mapToSumOfValue(givenContinentCase)
                .map(CountryReport::getPopulation)
                .toList();
        var allVaccinatedOfContinent = mapToSumOfValue(givenContinentVaccineCase)
                .map(CountryReport::getVaccinated)
                .toList();


        var calculationReport = CalculationReport
                .builder()
                .name(continent)
                .confirmedList(allConfirmedOfContinent)
                .recoveredList(allRecoveredOfContinent)
                .deathList(allDeathsOfContinent)
                .populationList(allPopulationOfContinent)
                .vaccinatedList(allVaccinatedOfContinent)
                .build();

        return coefficientCalculator.calculateCoefficient(calculationReport);
    }

    private Stream<CountryReport> mapToSumOfValue(Map<String, Country> map) {
        return map
                .values()
                .stream()
                .map(Country::getAll);
    }

//    private void keepIng() {
//        var completeMap = givenContinentCase
//                .values()
//                .stream()
//                .map(Country::getAll)
//                .map(this::getContinentReport)
//                .map(Map::values)
//                .toList();
//    }

//    private Map<String, Long> getContinentReport(CountryReport countryReport) {
//        Map<String, Long> report = new HashMap<>();
//        report.put("confirmed", Long.valueOf(countryReport.getConfirmed()));
//        report.put("recovered", Long.valueOf(countryReport.getRecovered()));
//        report.put("deaths", Long.valueOf(countryReport.getDeaths()));
//        report.put("population", Long.valueOf(countryReport.getPopulation()));
//
//        return report;
//    }
}
