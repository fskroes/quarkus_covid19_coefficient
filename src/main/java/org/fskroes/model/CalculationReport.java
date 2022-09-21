package org.fskroes.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculationReport {

    private String name;
    private long sumConfirmed;
    private long sumRecovered;
    private long sumDeath;
    private long sumPopulation;
    private long vaccinated;

}
