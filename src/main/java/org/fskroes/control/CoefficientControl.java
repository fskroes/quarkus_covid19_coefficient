package org.fskroes.control;

import org.fskroes.entity.Country;
import org.fskroes.entity.CountryReport;
import org.fskroes.model.CalculationReport;
import org.fskroes.model.CaseCoefficient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ApplicationScoped
public class CoefficientControl {

    private final String world = "World";

    @Inject
    CoefficientCalculator coefficientCalculator;

    public CaseCoefficient getCoefficientForContinent(
            String continent,
            Map<String, Country> givenContinentCase,
            Map<String, Country> givenContinentVaccineCase
    ) {

        var allConfirmedOfContinent = mapToAllOfValue(givenContinentCase)
                .map(CountryReport::getConfirmed)
                .toList();
        var allRecoveredOfContinent = mapToAllOfValue(givenContinentCase)
                .map(CountryReport::getRecovered)
                .toList();
        var allDeathsOfContinent = mapToAllOfValue(givenContinentCase)
                .map(CountryReport::getDeaths)
                .toList();
        var allPopulationOfContinent = mapToAllOfValue(givenContinentCase)
                .map(CountryReport::getPopulation)
                .toList();
        var allVaccinatedOfContinent = mapToAllOfValue(givenContinentVaccineCase)
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

    public CaseCoefficient getCoefficientForAllCountries(
            Map<String, Country> allLiveCases,
            List<Map<String, Country>> allVaccineCases)
    {
        var allConfirmedOfCountries = mapToAllOfValue(allLiveCases)
                .map(CountryReport::getConfirmed)
                .toList();
        var allRecoveredOfCountries = mapToAllOfValue(allLiveCases)
                .map(CountryReport::getRecovered)
                .toList();
        var allDeathsOfCountries = mapToAllOfValue(allLiveCases)
                .map(CountryReport::getDeaths)
                .toList();
        var allPopulationOfCountries = mapToAllOfValue(allLiveCases)
                .map(CountryReport::getPopulation)
                .toList();
        var allVaccinatedOfCountries = allVaccineCases
                .stream()
                .map(this::mapToAllOfValue)
                .map(countryReportStream -> countryReportStream
                        .map(CountryReport::getVaccinated)
                        .toList()
                )
                .flatMap(Collection::stream)
                .toList();

        var calculationReport = CalculationReport
                .builder()
                .name(world)
                .confirmedList(allConfirmedOfCountries)
                .recoveredList(allRecoveredOfCountries)
                .deathList(allDeathsOfCountries)
                .populationList(allPopulationOfCountries)
                .vaccinatedList(allVaccinatedOfCountries)
                .numberOfCountries(Math.max(allConfirmedOfCountries.size(), allVaccinatedOfCountries.size()))
                .build();

        return coefficientCalculator.calculateCoefficient(calculationReport);
    }

    private Stream<CountryReport> mapToAllOfValue(Map<String, Country> map) {
        return map
                .values()
                .stream()
                .map(Country::getAll);
    }
}
