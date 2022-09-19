package org.fskroes.model;

import io.quarkus.qson.QsonProperty;

import java.util.List;

public class CasesVaccined {

    @QsonProperty("All")
    private AllCountryInformation allCountryInformation;

    public AllCountryInformation getAllCountryInformation() {
        return allCountryInformation;
    }

    public void setAllCountryInformation(AllCountryInformation allCountryInformation) {
        this.allCountryInformation = allCountryInformation;
    }
}
