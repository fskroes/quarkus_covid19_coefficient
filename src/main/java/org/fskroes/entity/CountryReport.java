package org.fskroes.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryReport {

    public long administered;
    @JsonProperty("people_vaccinated")
    public long vaccinated;
    public long confirmed;
    public long recovered;
    public long deaths;
    public String country;
    public long population;
    public String continent;

    @JsonProperty("sq_km_area")
    public int area;

    @JsonProperty("life_expectancy")
    public double lifeExpectancy;

    @JsonProperty("elevation_in_meters")
    public String elevation;

    public String abbreviation;
    public int iso;
    public String location;
    @JsonProperty("capital_city")
    public String capitalCity;
    @JsonProperty("lat")
    public double latitude;
    @JsonProperty("long")
    public double longitude;
    public String updated;
}
