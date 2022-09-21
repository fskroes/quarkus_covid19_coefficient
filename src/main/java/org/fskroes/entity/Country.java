package org.fskroes.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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
