package org.fskroes.model;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CaseCoefficient {

    private double coefficient;
    private String name;

}
