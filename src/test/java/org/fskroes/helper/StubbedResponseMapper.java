package org.fskroes.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fskroes.entity.Country;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

import static org.fskroes.helper.StubbedCases.STUBBED_EUROPE_CASES;
import static org.fskroes.helper.StubbedVaccinedCases.STUBBED_VACCINED_CONTINENT_CASES;

@ApplicationScoped
public class StubbedResponseMapper {

    @Inject
    ObjectMapper jacksonMapper;

    public Map<String, Country> getStubVaccineContinentResponse() {
        try {
            return jacksonMapper.readValue(STUBBED_VACCINED_CONTINENT_CASES, new TypeReference<Map<String, Country>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Country> getStubContinentResponse() {
        try {
            return jacksonMapper.readValue(STUBBED_EUROPE_CASES, new TypeReference<Map<String, Country>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
