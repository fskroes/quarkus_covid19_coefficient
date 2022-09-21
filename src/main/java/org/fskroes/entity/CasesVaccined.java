package org.fskroes.entity;

import io.quarkus.qson.QsonProperty;

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
