package org.fskroes.control;

import org.fskroes.model.CaseCoefficient;
import org.fskroes.entity.CasesVaccined;
import org.fskroes.entity.Country;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoefficientCalculator {


    public CaseCoefficient getCoefficientCalculator(Country countryCase, CasesVaccined countryVaccineCase) {

        var deaths = (long) countryCase.getAll().getDeaths();
        var population = (long) countryCase.getAll().getPopulation();
        var confirmedVaccinated = (long) countryVaccineCase.getAllCountryInformation().getVaccinated();
        var recovered = (long) countryCase.getAll().getRecovered();
        var confirmedCases = (long) countryCase.getAll().getConfirmed();

        var caseCoefficient = new CaseCoefficient();
        caseCoefficient.setName(countryCase.getAll().getCountry());
        caseCoefficient.setCoefficient(calculateCoefficient(
                confirmedCases,
                deaths,
                population,
                confirmedVaccinated,
                recovered)
        );

        return caseCoefficient;
    }

    private double calculateCoefficient(long confirmedCases, long deaths, long population, long confirmedVaccinated, long recovered) {

        var percentageDeathOfPop = (double) deaths / (population + deaths) * 100;
        var percentageVaccinated = (double) confirmedVaccinated / population * 100;
        var populationNotVaccinated = population - confirmedVaccinated;
        var percentageOfPopulationRecovered = (double) recovered / population * 100;
        var d = confirmedVaccinated - confirmedCases - recovered;

        var coefficient_ratio = deaths / confirmedVaccinated;

        // A standardized value is what you get when you take a data point and scale it by population data. It tells us how far from the mean we are in terms of standard deviations.
        var mean = confirmedVaccinated + deaths + confirmedCases + recovered / population;
        var standardizedDeath = mean - deaths / population;


        var coefficient =
                (percentageDeathOfPop * percentageVaccinated) *
                (populationNotVaccinated * percentageOfPopulationRecovered) /
                population;
        return percentageDeathOfPop * percentageVaccinated / population;
    }
}
