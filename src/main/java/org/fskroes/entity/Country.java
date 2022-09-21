package org.fskroes.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {

    @JsonProperty("All")
    public CountryReport all;
    public void setAll(CountryReport all) {
        this.all = all;
    }

    public CountryReport getAll() {
        return all;
    }
}
