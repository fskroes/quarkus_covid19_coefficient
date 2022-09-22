package org.fskroes.control;

import io.quarkus.test.junit.QuarkusTest;
import org.fskroes.model.CalculationReport;
import org.fskroes.model.CaseCoefficient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class CoefficientCalculatorTest {

    private static final String CONTINENT = "Europe";
    @Inject
    CoefficientCalculator coefficientCalculator;

    @Test
    void calculateCoefficient_givenValidCalculationReport_returnCalculatedCoefficient() {

        var calculationReport = CalculationReport
                .builder()
                .name(CONTINENT)
                .confirmedList(List.of(2376432L, 1232341L, 13123231L))
                .recoveredList(List.of(123L, 3421L, 642L))
                .deathList(List.of(76543L, 4312L, 556L))
                .populationList(List.of(65432L, 76543L, 234421L))
                .vaccinatedList(List.of(5432L, 1234L, 7654L))
                .numberOfCountries(3)
                .build();

        var expectedResult = CaseCoefficient
                .builder()
                .name("Europe")
                .coefficient(1.5366971809287894E25)
                .build();

        var response =
                coefficientCalculator.calculateCoefficient(calculationReport);

        assertNotNull(response);
        assertEquals(expectedResult, response);
    }

    @Test
    void calculateCoefficient_givenNullForListInCalculationReport_returnsNaNAsCoefficient() {

        var calculationReport = CalculationReport
                .builder()
                .name(CONTINENT)
                .confirmedList(null)
                .recoveredList(List.of(123L, 3421L, 642L))
                .deathList(List.of(76543L, 4312L, 556L))
                .populationList(List.of(65432L, 76543L, 234421L))
                .vaccinatedList(List.of(5432L, 1234L, 7654L))
                .numberOfCountries(3)
                .build();

        var expectedResult = CaseCoefficient
                .builder()
                .name(CONTINENT)
                .coefficient(Double.NaN)
                .build();

        var response =
                coefficientCalculator.calculateCoefficient(calculationReport);

        assertNotNull(response);
        assertEquals(expectedResult, response);
    }
}
