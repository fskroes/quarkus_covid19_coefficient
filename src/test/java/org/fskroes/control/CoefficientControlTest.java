package org.fskroes.control;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.fskroes.helper.StubbedResponseMapper;
import org.fskroes.model.CalculationReport;
import org.fskroes.model.CaseCoefficient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@QuarkusTest
public class CoefficientControlTest {

    private static final String CONTINENT = "Europe";
    @Inject
    CoefficientControl coefficientControl;

    @InjectMock
    CoefficientCalculator coefficientCalculator;

    @Inject
    StubbedResponseMapper stubbedResponseMapper;

    @Test
    void getCoefficient() {
        var calculationReport = CalculationReport
                .builder()
                .name(CONTINENT)
                .sumConfirmed(345678)
                .sumRecovered(467890)
                .sumDeath(7895)
                .sumPopulation(234241231)
                .vaccinated(34567)
                .build();

        doReturn(calculationReport)
                .when(coefficientCalculator)
                .getCoefficientCalculator(any());

        var response = coefficientControl.getCoefficient(
                CONTINENT,
                stubbedResponseMapper.getStubContinentResponse(),
                stubbedResponseMapper.getStubVaccineContinentResponse()
        );

        Assertions.assertNotNull(response);
    }
}
