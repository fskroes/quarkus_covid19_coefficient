package org.fskroes.control;

import org.fskroes.entity.CaseCoefficient;
import org.fskroes.model.AllCountryInformation;
import org.fskroes.model.Country;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoefficientCalculator {

    public CaseCoefficient getCoefficientCalculator(Country country, AllCountryInformation vaccinedCasesForCountry) {

        var deaths = country.getAll().getDeaths();
        var population = country.getAll().getPopulation();
        var confirmedVaccinated = vaccinedCasesForCountry.getVaccinated();

        var caseCoefficient = new CaseCoefficient();
        caseCoefficient.setName(country.getAll().getCountry());
        caseCoefficient.setCoefficient(calculateCoefficient(deaths, population, confirmedVaccinated));

        return caseCoefficient;
    }

    private double calculateCoefficient(int deaths, int population, int confirmedVaccinated) {
        var percentageOfDeathOfPopulation = (deaths / population);
        var percentageOfVaccinedPeopleOfPopulation = (confirmedVaccinated / population);

        return percentageOfDeathOfPopulation * percentageOfVaccinedPeopleOfPopulation / population;
    }
}
