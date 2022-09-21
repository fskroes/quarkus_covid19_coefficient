package org.fskroes.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CalculationReport {

    private String name;
    private List<Long> confirmedList;
    private List<Long> recoveredList;
    private List<Long> deathList;
    private List<Long> populationList;
    private List<Long> vaccinatedList;
    private int numberOfCountries;

}
