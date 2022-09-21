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
                .numberOfCountries(Math.max(allConfirmedOfContinent.size(), allVaccinatedOfContinent.size()))
                .build();

        return coefficientCalculator.calculateCoefficient(calculationReport);
    }

    private Stream<CountryReport> mapToSumOfValue(Map<String, Country> map) {
        return map
                .values()
                .stream()
                .map(Country::getAll);
    }
}
