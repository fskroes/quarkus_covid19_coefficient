package org.fskroes.helper;

import io.quarkus.qson.runtime.QuarkusQsonMapper;
import org.fskroes.model.Cases;
import org.fskroes.model.CasesVaccined;
import org.fskroes.model.Country;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

import static org.fskroes.helper.StubbedCases.STUBBED_EUROPE_CASES;
import static org.fskroes.helper.StubbedCases.STUBBED_FRANCE_CASES;
import static org.fskroes.helper.StubbedVaccinedCases.STUBBED_VACCINED_FRANCE_CASES;

@ApplicationScoped
public class StubbedResponseMapper {

    @Inject
    QuarkusQsonMapper mapper;

    public Country getStubCountryResponse() {
        try {
            return mapper.parserFor(Country.class).read(STUBBED_FRANCE_CASES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CasesVaccined getStubVaccineResponse() {
        try {
            return mapper.parserFor(CasesVaccined.class).read(STUBBED_VACCINED_FRANCE_CASES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Cases getStubContinentResponse() {
        try {
            return mapper.parserFor(Cases.class).read(STUBBED_EUROPE_CASES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
