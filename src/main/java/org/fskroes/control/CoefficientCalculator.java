package org.fskroes.control;

import org.fskroes.model.CalculationReport;
import org.fskroes.model.CaseCoefficient;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CoefficientCalculator {

    public CaseCoefficient calculateCoefficient(CalculationReport calculationReport) {
        var calc = calc(
                calculationReport.getConfirmedList(),
                calculationReport.getDeathList(),
                calculationReport.getPopulationList(),
                calculationReport.getVaccinatedList(),
                calculationReport.getRecoveredList(),
                calculationReport.getNumberOfCountries()
        );

        return CaseCoefficient
                .builder()
                .name(calculationReport.getName())
                .coefficient(calc)
                .build();
    }

    private double calc(
            List<Long> confirmedList,
            List<Long> deathList,
            List<Long> populationList,
            List<Long> vaccinatedList,
            List<Long> recoveredList,
            int sumOfCountries
    ) {

//        var percentageDeathOfPop = (double) deaths / (population + deaths) * 100;
//        var percentageVaccinated = (double) confirmedVaccinated / population * 100;
//        var populationNotVaccinated = population - confirmedVaccinated;
//        var percentageOfPopulationRecovered = (double) recovered / population * 100;
//        var d = confirmedVaccinated - confirmedCases - recovered;
//
//        var coefficient_ratio = deaths / confirmedVaccinated;



        // sum
        var sumConfirmed = calculateSum(confirmedList);
        var sumDeaths = calculateSum(deathList);
        var sumPopulation = calculateSum(populationList);
        var sumVaccinated = calculateSum(vaccinatedList);
        var sumRecovered = calculateSum(recoveredList);

        // A standardized value is what you get when you take a data point and scale it by population data. It tells us how far from the mean we are in terms of standard deviations.
        var overallMean = sumConfirmed + sumDeaths + sumPopulation +
                sumVaccinated + sumRecovered / sumOfCountries;

        // mean
        var meanOfConfirmed = calculateMean(sumConfirmed, sumOfCountries);
        var meanOfDeath = calculateMean(sumDeaths, sumOfCountries);
        var meanOfPopulation = calculateMean(sumPopulation, sumOfCountries);
        var meanOfVaccinated = calculateMean(sumVaccinated, sumOfCountries);
        var meanOfRecovered = calculateMean(sumRecovered, sumOfCountries);

        // standard deviation
        var standardDeviationConfirmed = calculateStandardDeviation(confirmedList, sumOfCountries);
        var standardDeviationDeath = calculateStandardDeviation(deathList, sumOfCountries);
        var standardDeviationPopulation = calculateStandardDeviation(populationList, sumOfCountries);
        var standardDeviationVaccinated = calculateStandardDeviation(vaccinatedList, sumOfCountries);
        var standardDeviationRecovered = calculateStandardDeviation(recoveredList, sumOfCountries);

        // standardized
        var standardizedConfirmed = calculateStandardized(sumConfirmed, meanOfConfirmed, standardDeviationConfirmed);
        var standardizedDeath = calculateStandardized(sumDeaths, meanOfDeath, standardDeviationDeath);
        var standardizedPopulation = calculateStandardized(sumPopulation, meanOfPopulation, standardDeviationPopulation);
        var standardizedVaccinated = calculateStandardized(sumVaccinated, meanOfVaccinated, standardDeviationVaccinated);
        var standardizedRecovered = calculateStandardized(sumRecovered, meanOfRecovered, standardDeviationRecovered);

        var correlationCoefficient = standardizedConfirmed *
                standardizedDeath *
                standardizedPopulation *
                standardizedVaccinated *
                standardizedRecovered;
        var cc = correlationCoefficient / overallMean;
        var cc2 = correlationCoefficient / sumPopulation;
        var cc3 = correlationCoefficient / (sumOfCountries - 1);


        return cc3;

//        var coefficient =
//                (percentageDeathOfPop * percentageVaccinated) *
//                (populationNotVaccinated * percentageOfPopulationRecovered) /
//                sumPopulation;
//        return percentageDeathOfPop * percentageVaccinated / sumPopulation;
    }

    private long calculateMean(long sum, int numberOfCountries) {
        return sum / numberOfCountries;
    }

    private Double calculateStandardized(long sum, long mean, Double standardDeviation) {
        return sum - mean / standardDeviation;
    }

    private Double calculateStandardDeviation(List<Long> confirmedList, int sumOfCountries) {
        var addedNumbers = confirmedList.stream().mapToLong(Long::longValue).sum();
        var squareAddedNumbers = Math.pow(addedNumbers, 2);
        var dividedByN = squareAddedNumbers / sumOfCountries;
        var squareNumByItself = confirmedList
                .stream()
                .map(c -> Math.pow(c, c))
                .mapToLong(Double::longValue)
                .sum();
        var step5 = squareNumByItself - dividedByN;
        var nMinusOne = sumOfCountries - 1;
        var variance = step5 / nMinusOne;
        var standardDeviation = Math.sqrt(variance);

        return standardDeviation;
    }

    private long calculateSum(List<Long> values) {
        return values.stream().mapToLong(Long::longValue).sum();
    }
}
