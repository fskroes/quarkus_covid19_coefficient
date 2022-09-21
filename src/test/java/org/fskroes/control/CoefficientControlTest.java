package org.fskroes.control;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.fskroes.helper.StubbedResponseMapper;
import org.fskroes.model.CaseCoefficient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.any;
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
    void calculateCoefficient_givenValidParamForContinent_returnsCoefficientForContinent() {
        var calculationReport = CaseCoefficient
                .builder()
                .name(CONTINENT)
                .coefficient(0.0)
                .build();

        doReturn(calculationReport)
                .when(coefficientCalculator)
                .calculateCoefficient(any());

        var response = coefficientControl.getCoefficientForContinent(
                CONTINENT,
                stubbedResponseMapper.getStubContinentResponse(),
                stubbedResponseMapper.getStubVaccineContinentResponse()
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(calculationReport, response);
    }
}
