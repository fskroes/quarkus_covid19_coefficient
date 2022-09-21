package org.fskroes.control;

import org.fskroes.entity.Country;
import org.fskroes.model.CaseCoefficient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class CoefficientControl {

    @Inject
    CoefficientCalculator coefficientCalculator;

    public CaseCoefficient getCoefficient(
            Map<String, Country> givenCountryLiveCases,
            Map<String, Country> vaccineCasesForCountry
    ) {


        return new CaseCoefficient();
    }
}
